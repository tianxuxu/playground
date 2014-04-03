package ch.rasc.mongodb.capped;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class Insert {
	public static void main(String[] args) throws UnknownHostException, MongoException {

		MongoClient mongo = new MongoClient("localhost");

		DB db = mongo.getDB("testdb");

		DBCollection collection;
		if (!db.collectionExists("log")) {
			BasicDBObject createOptions = new BasicDBObject();
			createOptions.append("capped", true);
			createOptions.append("size", 1000);
			collection = db.createCollection("log", createOptions);
		} else {
			collection = db.getCollection("log");
		}

		CommandResult cr = collection.getStats();
		System.out.println("Capped: " + cr.getBoolean("capped", false));

		for (int j = 0; j < 1000; j++) {
			BasicDBObject logMessage = new BasicDBObject();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insert(logMessage);
		}

		try (DBCursor cursor = collection.find()) {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				System.out.println(obj.get("index"));
			}
		}

		BasicDBObject order = new BasicDBObject();
		order.append("$natural", -1);
		try (DBCursor cursor = collection.find().sort(order)) {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				System.out.println(obj.get("index"));
			}
		}

		BasicDBObject query = new BasicDBObject();
		query.append("index", new BasicDBObject("$gt", 990));
		
		try {
			WriteResult wr = collection.remove(query);
			System.out.println(wr.getN());
		} catch (MongoException e) {
			System.out.println(e.getCode());
		}
		

		// collection.drop();
		mongo.close();
	}
}
