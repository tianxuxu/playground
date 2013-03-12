package ch.rasc.twitterstream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;

public class Twitter4jSampleStreamExample {

	// A bare bones listener
	private static StatusListener listener1 = new StatusListener() {
		@Override
		public void onStatus(Status status) {
			// System.out.println("listener1: " + status);
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		}

		@Override
		public void onTrackLimitationNotice(int limit) {
		}

		@Override
		public void onScrubGeo(long user, long upToStatus) {
		}

		@Override
		public void onException(Exception e) {
		}
	};

	// A bare bones StatusStreamHandler, which extends listener and gives some
	// extra functionality
	private static StatusListener listener2 = new StatusStreamHandler() {
		@Override
		public void onStatus(Status status) {
			System.out.println(status.getText());
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		}

		@Override
		public void onTrackLimitationNotice(int limit) {
		}

		@Override
		public void onScrubGeo(long user, long upToStatus) {
		}

		@Override
		public void onException(Exception e) {
		}

		@Override
		public void onDisconnectMessage(DisconnectMessage message) {
		}

		@Override
		public void onUnknownMessageType(String s) {
		}
	};

	public static void oauth(String consumerKey, String consumerSecret, String token, String secret)
			throws InterruptedException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);

		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

		endpoint.trackTerms(Lists.newArrayList("java"));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

		BasicClient client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		ExecutorService es = Executors.newSingleThreadExecutor();
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue, Lists.newArrayList(listener1,
				listener2), es);

		t4jClient.connect();
		t4jClient.process();

		Thread.sleep(10000);

		t4jClient.stop();
		client.stop();
		// es.shutdownNow();
	}

	public static void main(String[] args) {
		try {
			Twitter4jSampleStreamExample.oauth(args[0], args[1], args[2], args[3]);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}