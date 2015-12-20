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
import com.mongodb.client.model.Projections;

public class InsertMax {
	public static void main(String[] args) throws MongoException {

		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		Set<String> collectionNames = new HashSet<>();
		db.listCollectionNames().forEach((Consumer<String>)(d-> collectionNames.add(d)));

		if (collectionNames.contains("log")) {
			db.getCollection("log").drop();
		}

		db.createCollection("log", new CreateCollectionOptions().capped(true)
				.sizeInBytes(1000L).maxDocuments(3));
		MongoCollection<Document> collection = db.getCollection("log");

		for (int j = 0; j < 10; j++) {
			Document logMessage = new Document();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insertOne(logMessage);
		}

		collection.find().projection(Projections.include("index"))
				.forEach((Consumer<Document>)(d -> System.out.println(d.get("index"))));

		Document cr = db.runCommand(new BsonDocument("collStats", new BsonString("log")));
		System.out.println(cr);
		System.out.println("Number of documents:   " + cr.get("count"));
		System.out.println("Total size in bytes:   " + cr.get("size"));
		System.out.println("Average size in bytes: " + cr.get("avgObjSize"));
	}
}
