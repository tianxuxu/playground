package ch.rasc.mongodb.blog;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class MainInsert {

	public static void main(String[] args) throws UnknownHostException, MongoException {
		MongoClient mongo = new MongoClient("localhost");
		// Mongo mongo = new Mongo("localhost", 10000);

		DB db = mongo.getDB("testdb");
		db.setWriteConcern(WriteConcern.SAFE);

		DBCollection collection = db.getCollection("users");
		collection.setWriteConcern(WriteConcern.SAFE);

		// Map<String,Object> userm = new HashMap<String,Object>();
		// userm.put("username", "johnd");
		// DBObject user = new BasicDBObject(userm);

		BasicDBObject user = new BasicDBObject("username", "johnd");
		user.append("_id", 1);
		user.append("firstName", "John");
		user.append("name", "Doe");
		user.append("enabled", false);
		user.append("noOfLogins", 0);
		user.append("lastLogin", new Date());
		user.append("groups", new String[] { "admin", "user" });

		System.out.println(user);
		WriteResult wr = collection.insert(user);
		// collection.insert(user, WriteConcern.SAFE);
		CommandResult lastError = wr.getLastError();
		System.out.println(lastError);
		// System.out.println(user);
		System.out.println(wr);

		// List<DBObject> users = new ArrayList<DBObject>();
		// users.add(user);
		// collection.insert(users);

		user = new BasicDBObject("username", "francol");
		user.append("firstName", "Franco");
		user.append("name", "Lawrence");
		user.append("enabled", true);
		user.append("noOfLogins", 0);
		user.append("lastLogin", new Date());
		user.append("groups", new String[] { "user" });
		collection.insert(user);

		mongo.close();

	}

}
