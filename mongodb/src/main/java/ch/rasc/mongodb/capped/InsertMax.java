package ch.rasc.mongodb.capped;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class InsertMax {
	public static void main(String[] args) throws UnknownHostException, MongoException {

		Mongo mongo = new Mongo("localhost");

		DB db = mongo.getDB("testdb");

		DBCollection collection;
		if (db.collectionExists("log")) {
			db.getCollection("log").drop();
		}

		BasicDBObject createOptions = new BasicDBObject();
		createOptions.append("capped", true);
		createOptions.append("size", 1000);
		createOptions.append("max", 3);
		collection = db.createCollection("log", createOptions);

		for (int j = 0; j < 10; j++) {
			BasicDBObject logMessage = new BasicDBObject();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insert(logMessage);
		}

		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			System.out.println(obj.get("index"));
		}

		CommandResult stats = collection.getStats();
		System.out.println("Anzahl Dokumente: " + stats.get("count"));
		System.out.println("Anzahl Bytes: " + stats.get("size"));
		System.out.println("Durchschnitt Bytes pro Dokument : " + stats.get("avgObjSize"));

		//		for (String key : stats.keySet()) {
		//			System.out.printf("%s = %s\n", key, stats.get(key));
		//		}

		mongo.close();
	}
}
