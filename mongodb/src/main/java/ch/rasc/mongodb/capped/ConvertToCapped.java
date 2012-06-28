package ch.rasc.mongodb.capped;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ConvertToCapped {
	public static void main(final String[] args) throws UnknownHostException, MongoException {

		Mongo mongo = new Mongo("localhost");

		DB db = mongo.getDB("testdb");

		DBCollection collection;
		if (db.collectionExists("log")) {
			db.getCollection("log").drop();
		}

		// non capped
		collection = db.getCollection("log");

		for (int j = 0; j < 10000; j++) {
			BasicDBObject logMessage = new BasicDBObject();
			logMessage.append("index", j);
			logMessage.append("message", "User sr");
			logMessage.append("loggedIn", new Date());
			logMessage.append("loggedOut", new Date());
			collection.insert(logMessage);
		}

		System.out.println("Count : " + collection.count());
		System.out.println("Capped: " + collection.getStats().getBoolean("capped", false));

		BasicDBObject ctcCommand = new BasicDBObject();
		ctcCommand.append("convertToCapped", "log");
		ctcCommand.append("size", 1000);
		db.command(ctcCommand);

		System.out.println("Count : " + collection.count());
		System.out.println("Capped: " + collection.getStats().getBoolean("capped", false));

		mongo.close();
	}
}
