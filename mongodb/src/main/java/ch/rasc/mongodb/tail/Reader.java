package ch.rasc.mongodb.tail;

import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.CursorType;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;

public class Reader {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase dbMongoDatabase = mongo.getDatabase("testdb");

		boolean dbexists = false;
		for (String name : dbMongoDatabase.listCollectionNames()) {
			if (name.equals("log")) {
				dbexists = true;
				break;
			}
		}

		if (!dbexists) {
			dbMongoDatabase.createCollection("log",
					new CreateCollectionOptions().capped(true).sizeInBytes(100_000));
		}

		MongoCollection<Document> collection = dbMongoDatabase.getCollection("log");

		collection.find().cursorType(CursorType.TailableAwait)
				.forEach((Consumer<Document>)(d -> System.out.println(d)));
		System.out.println("END");
	}

}
