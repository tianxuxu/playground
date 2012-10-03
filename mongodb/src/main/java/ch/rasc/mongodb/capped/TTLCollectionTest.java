package ch.rasc.mongodb.capped;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class TTLCollectionTest {

	public static void main(String[] args) throws UnknownHostException {
		
		Mongo mongo = new Mongo("localhost");

		DB db = mongo.getDB("testdb");
		DBCollection collection = db.getCollection("log");

		//TTL Index		
		BasicDBObject index = new BasicDBObject("date", 1);
		BasicDBObject options = new BasicDBObject("expireAfterSeconds", TimeUnit.MINUTES.toSeconds(1));
		collection.ensureIndex(index, options);
				
//		for (int j = 0; j < 10; j++) {
//		  BasicDBObject logMessage = new BasicDBObject();
//		  logMessage.append("date", new Date());
//		  logMessage.append("message", String.valueOf(j));
//		  collection.insert(logMessage);
//		}
		
//		Mongo mongo = new Mongo("localhost");
//
//		DB db = mongo.getDB("testdb");
//
//		DBCollection collection;
//		if (db.collectionExists("log")) {
//			db.getCollection("log").drop();
//		}
//		
//		collection = db.getCollection("log");
//		
//		BasicDBObject index = new BasicDBObject("date", 1);
//		BasicDBObject options = new BasicDBObject("expireAfterSeconds", TimeUnit.MINUTES.toSeconds(1));
//		collection.ensureIndex(index, options);
//		
//		for (int j = 0; j < 10; j++) {
//			BasicDBObject logMessage = new BasicDBObject();
//			logMessage.append("date", new Date());
//			logMessage.append("message", String.valueOf(j));
//			collection.insert(logMessage);
//		}
//		
//		BasicDBObject logMessage = new BasicDBObject();
//		logMessage.append("date", DateTime.now().plusDays(1).toDate());
//		logMessage.append("message", "tomorrow");
//		collection.insert(logMessage);

//		DBCursor cursor = collection.find();
//		while (cursor.hasNext()) {
//			DBObject obj = cursor.next();
//			System.out.println(obj.get("index"));
//		}
//
//		CommandResult stats = collection.getStats();
//		System.out.println("Anzahl Dokumente: " + stats.get("count"));
//		System.out.println("Anzahl Bytes: " + stats.get("size"));
//		System.out.println("Durchschnitt Bytes pro Dokument : " + stats.get("avgObjSize"));
//
//		// for (String key : stats.keySet()) {
//		// System.out.printf("%s = %s\n", key, stats.get(key));
//		// }

		mongo.close();
	}

}
