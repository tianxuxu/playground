package ch.rasc.twitterstream;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.common.collect.ImmutableList;
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
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class Twitter4jSampleStreamExample {

	private final static Pattern URL_PATTERN = Pattern.compile(
			"(((http[s]?:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)");

	// A bare bones StatusStreamHandler, which extends listener and gives some
	// extra functionality
	private static StatusListener listener2 = new StatusStreamHandler() {
		@Override
		public void onStatus(Status status) {
			System.out.println(status.getUser().getName());
			String text = status.getText();
			text = text.replace("<", "&lt;");
			System.out.println(text);

			Matcher matcher = URL_PATTERN.matcher(text);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String unshortenedURL = unshorten(matcher.group());
				if (unshortenedURL != null) {
					matcher.appendReplacement(sb, "<a href=\"" + unshortenedURL + "\">"
							+ unshortenedURL + "</a>");
				}
				else {
					matcher.appendReplacement(sb, "$0");
				}
			}
			matcher.appendTail(sb);
			System.out.println(sb.toString());
			System.out.println();
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			// nothing here
		}

		@Override
		public void onTrackLimitationNotice(int limit) {
			// nothing here
		}

		@Override
		public void onScrubGeo(long user, long upToStatus) {
			// nothing here
		}

		@Override
		public void onException(Exception e) {
			// nothing here
		}

		@Override
		public void onDisconnectMessage(DisconnectMessage message) {
			// nothing here
		}

		@Override
		public void onUnknownMessageType(String s) {
			// nothing here
		}

		@Override
		public void onStallWarning(StallWarning stallWarning) {
			System.out.println(stallWarning.toString());
		}

		@Override
		public void onStallWarningMessage(StallWarningMessage stallWarning) {
			System.out.println(stallWarning.toString());
		}
	};

	private static void oauth(String consumerKey, String consumerSecret, String token,
			String secret) throws InterruptedException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>(100);

		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		endpoint.trackTerms(ImmutableList.of("ExtJS", "Sencha", "atmo_framework", "#java",
				"java7", "java8", "websocket", "#portal", "html5", "javascript"));
		endpoint.languages(ImmutableList.of("en", "de"));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

		BasicClient client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		ExecutorService es = Executors.newSingleThreadExecutor();
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue,
				ImmutableList.of(listener2), es);

		t4jClient.connect();
		t4jClient.process();

		TimeUnit.MINUTES.sleep(30);

		t4jClient.stop();
		client.stop();
		// es.shutdownNow();
	}

	public static void main(String[] args) {
		try {
			Twitter4jSampleStreamExample.oauth(args[0], args[1], args[2], args[3]);
		}
		catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public static String unshorten(String url) {
		try {
			HttpHead head = new HttpHead(url);
			HttpParams params = new BasicHttpParams();
			HttpClientParams.setRedirecting(params, false);
			head.setParams(params);
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			HttpResponse response = defaultHttpClient.execute(head);

			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_MOVED_PERMANENTLY
					|| status == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null) {
					String value = locationHeader.getValue();
					if (!value.startsWith("http") && value.startsWith("/")) {
						value = "http:/" + value;
					}
					return unshorten(value);
				}
			}
			else if (status >= 400 && status != HttpStatus.SC_METHOD_NOT_ALLOWED
					&& status != HttpStatus.SC_FORBIDDEN) {
				System.out.println("STATUS >= 400");
				System.out.println(status);
				System.out.println(url);

				return null;
			}

		}
		catch (IllegalStateException | IOException e) {
			if (!(e instanceof SSLException || e instanceof ConnectException)) {
				System.out.println("Exception with url: " + url);
				e.printStackTrace();
			}
		}
		return url;
	}
}