package ch.rasc.twitter4j;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Snippet {
	public static void main(String[] args) throws TwitterException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("ionic2");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {
			System.out.println(
					"@" + status.getUser().getScreenName() + ":" + status.getText());
		}

		List<Status> statuses = twitter.getUserTimeline("Ionicframework");
		System.out.println("Showing Ionicframework timeline.");
		for (Status status : statuses) {
			System.out.println(status.getUser().getName() + ":" + status.getText());
		}
	}
}
