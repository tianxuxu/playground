package ch.rasc.mongodb.sandbox;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class Main3 {

	public static void main(String[] args) throws MongoException, IOException {
		MongoClient mongo = new MongoClient("localhost");
		DB db = mongo.getDB("tutorial");

		DBCollection collection = db.getCollection("users");
		collection.drop();

		BasicDBObject dbObj = new BasicDBObject("last_name", "smith");
		dbObj.append("age", 30);
		collection.save(dbObj);

		dbObj = new BasicDBObject("last_name", "jones");
		dbObj.append("age", 40);
		collection.save(dbObj);

		// DBCursor cursor = collection.find();
		// while(cursor.hasNext()) {
		// System.out.println(cursor.next());
		// }

		BasicDBObject query = new BasicDBObject("last_name", "smith");
		BasicDBObject update = new BasicDBObject("$set",
				new BasicDBObject("city", "Chicago"));
		collection.update(query, update, false, true);

		try (DBCursor cursor = collection
				.find(new BasicDBObject("age", new BasicDBObject("$gt", 20)))) {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				ObjectId oid = (ObjectId) obj.get("_id");
				System.out.println(oid.getDate());
			}
		}

		mongo.close();
	}

	// private static void simpleFile() throws MongoException, IOException {
	//
	//
	//
	//
	// long start = System.currentTimeMillis();
	// for (int i = 0; i < 100000; i++) {
	// BasicDBObject dbObj = new BasicDBObject("myKey", "aValue");
	// collection.insert(dbObj);
	// }
	// CommandResult cr = db.getLastError(WriteConcern.FSYNC_SAFE);
	// System.out.println(cr);
	//
	// BasicDBObject sync = new BasicDBObject("fsync", 1);
	// cr = mongo.getDB("admin").command(sync);
	// System.out.println(cr);
	// sync = new BasicDBObject("fsync", 1).append("async", true);
	// cr = mongo.getDB("admin").command(sync);
	// System.out.println(cr);
	// Double success = (Double)cr.get("ok");
	// System.out.println(success);
	//
	// long duration = System.currentTimeMillis() - start;
	// System.out.println(duration + " ms");
	//
	//
	// }

}
