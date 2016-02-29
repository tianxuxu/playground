package ch.rasc.mongodb.capped;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class TTLCollectionTest {

	public static void main(String[] args) throws InterruptedException {

		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) throws InterruptedException {
		MongoDatabase db = mongo.getDatabase("testdb");
		MongoCollection<Document> collection = db.getCollection("log");

		// TTL Index
		collection.createIndex(new Document("date", 1),
				new IndexOptions().expireAfter(1L, TimeUnit.MINUTES));

		for (int j = 0; j < 10; j++) {
			Document logMessage = new Document();
			logMessage.append("date", new Date());
			logMessage.append("message", String.valueOf(j));
			System.out.println("INSERT: " + logMessage);
			collection.insertOne(logMessage);
			TimeUnit.SECONDS.sleep(10);
		}

		System.out.println("LIST ALL");
		collection.find().forEach((Consumer<Document>) d -> System.out.println(d));
	}

}
