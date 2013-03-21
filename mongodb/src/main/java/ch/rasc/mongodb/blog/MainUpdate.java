package ch.rasc.mongodb.blog;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MainUpdate {
	public static void main(String[] args) throws UnknownHostException, MongoException {
		MongoClient mongo = new MongoClient("localhost");

		DB db = mongo.getDB("testdb");
		DBCollection collection = db.getCollection("users");

		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.append("username", "johnd");

		BasicDBObject update = new BasicDBObject();
		update.append("$set", new BasicDBObject("lastLogin", new Date()));

		// collection.update(updateQuery, update);
		collection.update(updateQuery, update, false, true);

		update = new BasicDBObject();
		update.append("$inc", new BasicDBObject("noOfLogins", -1));

		collection.update(updateQuery, update);

		updateQuery = new BasicDBObject();
		updateQuery.append("username", "johnd");

		update = new BasicDBObject();
		update.append("$set", new BasicDBObject("country", "US"));

		// DBObject user = collection.findAndModify(updateQuery, update);
		DBObject user = collection.findAndModify(updateQuery, null, null, false, update, true, false);
		System.out.println(user);

		mongo.close();

	}
}
