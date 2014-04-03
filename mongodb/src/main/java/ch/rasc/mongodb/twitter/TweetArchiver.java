package ch.rasc.mongodb.twitter;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class TweetArchiver {

	public static void main(String[] args) throws UnknownHostException, MongoException {

		MongoClient mongo = new MongoClient("localhost");
		DB db = mongo.getDB("tutorial");

		DBCollection collection = db.getCollection("tweets");
		collection.drop();

		DBObject options = new BasicDBObject();
		options.put("unique", Boolean.TRUE);

		collection.createIndex(new BasicDBObject("id", 1), options);

		Twitter twitter = new TwitterTemplate(args[0], args[1], args[2], args[3]);
		List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("feliciaday", 200);
		for (Tweet tweet : tweets) {
			BasicDBObject obj = new BasicDBObject("text", tweet.getText());
			obj.append("fromUser", tweet.getFromUser());
			obj.append("id", tweet.getId());
			obj.append("source", tweet.getSource());
			collection.insert(obj);
		}

		try (DBCursor cursor = collection.find(new BasicDBObject(), new BasicDBObject("text", 1))) {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				System.out.println(obj.get("text"));
				System.out.println("-------------------");
			}
		}
	}

}
