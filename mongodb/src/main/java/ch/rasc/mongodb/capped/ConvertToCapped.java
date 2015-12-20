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

public class ConvertToCapped {
	public static void main(String[] args) throws MongoException {

		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		Set<String> collectionNames = new HashSet<>();
		db.listCollectionNames().forEach((Consumer<String>)(d-> collectionNames.add(d)));

		MongoCollection<Document> collection;
		if (collectionNames.contains("log")) {
			db.getCollection("log").drop();
		}
		collection = db.getCollection("log");

		for (int j = 0; j < 10000; j++) {
			Document logMessage = new Document();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insertOne(logMessage);
		}

		Document cr = db.runCommand(new BsonDocument("collStats", new BsonString("log")));
		System.out.println("Count : " + collection.count());
		System.out.println("Capped: " + cr.getBoolean("capped", false));

		db.runCommand(new Document("convertToCapped", "log").append("size", 1000));
		cr = db.runCommand(new BsonDocument("collStats", new BsonString("log")));
		System.out.println("Count : " + collection.count());
		System.out.println("Capped: " + cr.getBoolean("capped", false));
	}
}
