package ch.rasc.mongodb.twitter;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.Document;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Sorts;

public class TweetArchiver {

	public static void main(String[] args) throws MongoException {

		MongoClient mongo = new MongoClient("localhost");
		MongoDatabase db = mongo.getDatabase("tutorial");

		MongoCollection<Document> collection = db.getCollection("tweets");
		collection.drop();

		collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));

		Twitter twitter = new TwitterTemplate(args[0], args[1], args[2], args[3]);
		List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("feliciaday",
				200);
		for (Tweet tweet : tweets) {
			Document doc = new Document("text", tweet.getText())
					.append("fromUser", tweet.getFromUser()).append("id", tweet.getId())
					.append("source", tweet.getSource());
			collection.insertOne(doc);
		}

		Block<Document> printBlock = new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document.get("text"));
				System.out.println("-------------------");
			}
		};

		collection.find().sort(Sorts.ascending("text")).forEach(printBlock);
		
		mongo.close();
	}

}
