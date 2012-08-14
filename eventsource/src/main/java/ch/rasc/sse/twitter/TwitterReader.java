package ch.rasc.sse.twitter;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Service
public class TwitterReader {

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private final Lock readLock = rwl.readLock();

	private final Lock writeLock = rwl.writeLock();

	private TwitterTemplate template = new TwitterTemplate();

	private long lastReceivedId = 0;

	private List<Tweet> tweets = Lists.newLinkedList();

	public ImmutableList<Tweet> getTweetsSinceId(long lastId) {
		ImmutableList.Builder<Tweet> builder = ImmutableList.builder();

		readLock.lock();
		try {
			for (Tweet tweet : tweets) {
				if (tweet.getId() > lastId) {
					builder.add(tweet);
				}
			}
		} finally {
			readLock.unlock();
		}

		return builder.build();
	}

	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void readTwitterFeed() {
		
		SearchResults results = template.searchOperations().search("java", 1, 50, lastReceivedId, 0);
		List<Tweet> newTweets = Lists.newLinkedList();
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
			} finally {
				writeLock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		new TwitterReader().readTwitterFeed();
	}

}
