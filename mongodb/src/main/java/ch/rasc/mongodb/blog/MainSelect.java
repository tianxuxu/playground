package ch.rasc.mongodb.blog;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MainSelect {

	public static void main(String[] args) throws UnknownHostException, MongoException {
		MongoClient mongo = new MongoClient("localhost");
		// MongoClient mongo = new MongoClient("localhost", 10000);

		DB db = mongo.getDB("testdb");

		DBCollection collection = db.getCollection("users");

		try (DBCursor cursor = collection.find()) {
			while (cursor.hasNext()) {
				DBObject doc = cursor.next();
				System.out.println(doc);
			}
		}

		System.out.println();

		BasicDBObject query = new BasicDBObject();
		query.append("username", "johnd");
		try (DBCursor cursor = collection.find(query)) {
			while (cursor.hasNext()) {
				DBObject doc = cursor.next();
				System.out.println(doc);
			}
		}

		System.out.println();

		query = new BasicDBObject();
		query.append("username", "johnd");
		DBObject doc = collection.findOne(query);
		System.out.println(doc);

		System.out.println();

		query = new BasicDBObject();
		query.append("username", "johnd");

		BasicDBObject fields = new BasicDBObject();
		fields.append("enabled", 0);
		fields.append("_id", 0);
		doc = collection.findOne(query, fields);
		System.out.println(doc);

		System.out.println();

		query = new BasicDBObject();
		query.append("username", "johnd");
		query.append("name", "Doe");
		try (DBCursor cursor = collection.find(query)) {
			while (cursor.hasNext()) {
				doc = cursor.next();
				System.out.println(doc);
			}
		}

		System.out.println();

		BasicDBObject u1 = new BasicDBObject("username", "francol");
		BasicDBObject u2 = new BasicDBObject("username", "johnd");
		query = new BasicDBObject();
		query.append("$or", new DBObject[] { u1, u2 });

		try (DBCursor cursor = collection.find(query)) {
			while (cursor.hasNext()) {
				doc = cursor.next();
				System.out.println(doc);
			}
		}

		System.out.println();

		BasicDBObject inQuery = new BasicDBObject("$in", new String[] { "admin" });
		query = new BasicDBObject("groups", inQuery);

		try (DBCursor cursor = collection.find(query)) {
			while (cursor.hasNext()) {
				doc = cursor.next();
				System.out.println(doc);
			}
		}

		System.out.println();

		Pattern pattern = Pattern.compile("^D.*", Pattern.CASE_INSENSITIVE);
		query = new BasicDBObject("name", pattern);

		try (DBCursor cursor = collection.find(query)) {

			BasicDBObject sort = new BasicDBObject("username", 1);
			cursor.limit(1).skip(10).sort(sort);

			while (cursor.hasNext()) {
				doc = cursor.next();
				System.out.println(doc);
			}
		}

		mongo.close();

	}

}
