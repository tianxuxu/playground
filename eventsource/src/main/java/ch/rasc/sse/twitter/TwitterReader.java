package ch.rasc.sse.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterReader {

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private final Lock readLock = rwl.readLock();

	private final Lock writeLock = rwl.writeLock();

	private final TwitterTemplate twitterTemplate;

	private long lastReceivedId = 0;

	private final List<Tweet> tweets = new LinkedList<>();

	@Autowired
	public TwitterReader(Environment environment) {
		String consumerKey = environment.getProperty("twitter.consumerKey");
		String consumerSecret = environment.getProperty("twitter.consumerSecret");
		String accessToken = environment.getProperty("twitter.accessToken");
		String accessTokenSecret = environment.getProperty("twtter.accessTokenSecret");
		twitterTemplate = new TwitterTemplate(consumerKey, consumerSecret, accessToken,
				accessTokenSecret);
	}

	public List<Tweet> getTweetsSinceId(long lastId) {
		List<Tweet> builder = new ArrayList<>();

		readLock.lock();
		try {
			for (Tweet tweet : tweets) {
				if (tweet.getId() > lastId) {
					builder.add(tweet);
				}
			}
		}
		finally {
			readLock.unlock();
		}

		return Collections.unmodifiableList(builder);
	}

	@Scheduled(fixedDelay = 10000)
	public void readTwitterFeed() {

		SearchResults results = twitterTemplate.searchOperations().search("java", 50,
				lastReceivedId, 0);
		List<Tweet> newTweets = new LinkedList<>();
		long maxId = 0;
		for (Tweet tweet : results.getTweets()) {
			if (tweet.getId() > lastReceivedId) {
				newTweets.add(0, tweet);
				if (tweet.getId() > maxId) {
					maxId = tweet.getId();
				}
			}
		}
		lastReceivedId = maxId;

		if (!newTweets.isEmpty()) {
			System.out.printf("Got %d new tweets\n", newTweets.size());
			writeLock.lock();
			try {
				tweets.addAll(newTweets);
			}
			finally {
				writeLock.unlock();
			}
		}
	}

}
