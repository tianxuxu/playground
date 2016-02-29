package ch.rasc.mongodb.capped;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;

public class Insert {
	public static void main(String[] args) throws MongoException {

		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		Set<String> collectionNames = new HashSet<>();
		db.listCollectionNames().forEach((Consumer<String>) d -> collectionNames.add(d));

		MongoCollection<Document> collection;
		if (!collectionNames.contains("log")) {
			db.createCollection("log",
					new CreateCollectionOptions().capped(true).sizeInBytes(1000L));
		}
		collection = db.getCollection("log");

		Document cr = db.runCommand(new BsonDocument("collStats", new BsonString("log")));
		System.out.println("Capped: " + cr.getBoolean("capped", false));

		for (int j = 0; j < 1000; j++) {
			Document logMessage = new Document();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insertOne(logMessage);
		}

		collection.find().projection(Projections.include("index"))
				.forEach((Consumer<Document>) d -> System.out.println(d.get("index")));

		collection.find().sort(Sorts.orderBy(new Document("$natural", -1)))
				.projection(Projections.include("index"))
				.forEach((Consumer<Document>) d -> System.out.println(d.get("index")));

		DeleteResult dr = collection.deleteMany(Filters.gt("index", 990));
		System.out.println(dr);
	}
}
