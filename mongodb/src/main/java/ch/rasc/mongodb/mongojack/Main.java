package ch.rasc.mongodb.mongojack;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public class Main {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);

			CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
					MongoClient.getDefaultCodecRegistry(),
					CodecRegistries.fromCodecs(new Item2Codec()));

			MongoDatabase db = mongo.getDatabase("testdb")
					.withCodecRegistry(codecRegistry);

			MongoCollection<Item2> collection = db.getCollection("items2", Item2.class);
			// collection.drop();

			// Item2 item = new Item2();
			// item.setData(1.2d);
			// item.setName("name");
			// collection.insertOne(item);
			// System.out.println(item);
			//
			//
			// for (Item2 i : collection.find()) {
			// System.out.println(i);
			// }

			for (Item2 i : collection.find().projection(Projections.exclude("name"))) {
				System.out.println(i);
			}
		}
	}

	private static void doSomething(MongoClient mongo) {

		MongoDatabase db = mongo.getDatabase("testdb");
		MongoCollection<Document> collection = db.getCollection("items");
		collection.drop();

		JacksonDBCollection<Item, String> coll = JacksonDBCollection.wrap(
				mongo.getDB("testdb").getCollection("items"), Item.class, String.class);
		Item item = new Item();
		item.setData(1.2d);
		item.setName("name");
		WriteResult<Item, String> result = coll.insert(item);
		String id = result.getSavedId();
		Item savedObject = result.getSavedObject();

		System.out.println(id);
		System.out.println(savedObject);

	}

}
