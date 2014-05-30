package ch.rasc.mongodb.blog;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MainIndex {
	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		MongoClient mongo = new MongoClient("localhost");

		DB db = mongo.getDB("testdb");

		DBCollection collection = db.getCollection("users");

		BasicDBObject index = new BasicDBObject("username", 1);
		collection.createIndex(index);
		// collection.dropIndex(index);

		BasicDBObject query = new BasicDBObject();
		query.append("username", "johnd");
		try (DBCursor cursor = collection.find(query)) {
			DBObject explain = cursor.explain();
			System.out.println(explain);

			List<DBObject> indexes = collection.getIndexInfo();
			for (DBObject ix : indexes) {
				System.out.println(ix);
			}
		}

		mongo.close();

	}
}
