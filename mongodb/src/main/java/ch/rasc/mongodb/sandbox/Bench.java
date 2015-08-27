package ch.rasc.mongodb.sandbox;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class Bench {

	public static void main(String[] args) throws MongoException, IOException {
		Bench.simpleFile();
	}

	private static void simpleFile() throws MongoException {
		MongoClient mongo = new MongoClient("localhost");

		DB db = mongo.getDB("testdb");
		DBCollection collection = db.getCollection("files");

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			BasicDBObject dbObj = new BasicDBObject("myKey", "aValue");
			collection.insert(dbObj);
		}

		BasicDBObject sync = new BasicDBObject("fsync", 1);
		CommandResult cr = mongo.getDB("admin").command(sync);
		System.out.println(cr);
		sync = new BasicDBObject("fsync", 1).append("async", Boolean.TRUE);
		cr = mongo.getDB("admin").command(sync);
		System.out.println(cr);
		Double success = (Double) cr.get("ok");
		System.out.println(success);

		long duration = System.currentTimeMillis() - start;
		System.out.println(duration + " ms");

		mongo.close();
	}

}
