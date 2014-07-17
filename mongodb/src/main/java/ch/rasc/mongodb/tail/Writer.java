package ch.rasc.mongodb.tail;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class Writer {

	public static void main(String[] args) throws UnknownHostException, MongoException {
		MongoClient mongo = new MongoClient("localhost");

		DB db = mongo.getDB("testdb");

		DBCollection collection;
		if (!db.collectionExists("log")) {
			BasicDBObject createOptions = new BasicDBObject();
			createOptions.append("capped", Boolean.TRUE);
			createOptions.append("size", 100000);
			collection = db.createCollection("log", createOptions);
		}
		else {
			collection = db.getCollection("log");
		}

		Random rand = new Random();

		int count = 0;
		while (true) {
			count++;
			System.out.println("Write: " + count);
			BasicDBObject dbObj = new BasicDBObject("date", new Date());
			dbObj.append("msg", count);
			collection.insert(dbObj);

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
