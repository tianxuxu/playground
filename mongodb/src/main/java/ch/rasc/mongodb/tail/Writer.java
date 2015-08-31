package ch.rasc.mongodb.tail;

import java.util.Date;
import java.util.Random;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;

public class Writer {

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

		Random rand = new Random();

		int count = 0;
		while (true) {
			count++;
			System.out.println("Write: " + count);
			Document dbObj = new Document("date", new Date());
			dbObj.append("msg", count);
			collection.insertOne(dbObj);

			int waitTime = rand.nextInt(30) + 1;
			try {
				Thread.sleep(waitTime * 1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
