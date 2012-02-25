package ch.ralscha.mongodbplayground.sandbox;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

public class Bench {

	public static void main(String[] args) throws MongoException, IOException {
		Bench.simpleFile();
	}

	private static void simpleFile() throws MongoException, IOException {
		Mongo mongo = new Mongo("localhost");

		DB db = mongo.getDB("testdb");
		DBCollection collection = db.getCollection("files");

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			BasicDBObject dbObj = new BasicDBObject("myKey", "aValue");
			collection.insert(dbObj);
		}
		CommandResult cr = db.getLastError(WriteConcern.FSYNC_SAFE);
		System.out.println(cr);

		BasicDBObject sync = new BasicDBObject("fsync", 1);
		cr = mongo.getDB("admin").command(sync);
		System.out.println(cr);
		sync = new BasicDBObject("fsync", 1).append("async", true);
		cr = mongo.getDB("admin").command(sync);
		System.out.println(cr);
		Double success = (Double) cr.get("ok");
		System.out.println(success);

		long duration = System.currentTimeMillis() - start;
		System.out.println(duration + " ms");

		mongo.close();
	}

}
