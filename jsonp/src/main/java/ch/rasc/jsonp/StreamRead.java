package ch.rasc.jsonp;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class StreamRead {
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {

		String apiKey = args[0];
		String latitude = "46.947922";
		String longitude = "7.444608";

		String urlTemplate = "https://api.forecast.io/forecast/%s/%s,%s?units=si&exclude=daily";
		String url = String.format(urlTemplate, apiKey, latitude, longitude);

		HttpGet httpget = new HttpGet(url);
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(httpget);
				JsonParser parser = Json.createParser(response.getEntity().getContent())) {

			boolean hourly = false;
			boolean data = false;
			String keyName = null;
			Calendar time = null;

			while (parser.hasNext()) {

				Event event = parser.next();

				switch (event) {

				case END_OBJECT:
					if (!data && hourly) {
						hourly = false;
					}
					break;
				case END_ARRAY:
					if (data) {
						data = false;
					}
					break;
				case KEY_NAME:
					keyName = parser.getString();
					if (keyName.equals("hourly")) {
						hourly = true;
					} else if (hourly && keyName.equals("data")) {
						data = true;
					}
					break;
				case VALUE_NUMBER:
					if (data && "time".equals(keyName)) {
						time = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
						time.setTimeInMillis(parser.getLong() * 1000);
					} else if (time != null && "temperature".equals(keyName)) {
						System.out.println(time.getTime() + " : " + parser.getBigDecimal());
						time = null;
					}
					break;
				case START_ARRAY:
					break;
				case START_OBJECT:
					break;
				case VALUE_FALSE:
					break;
				case VALUE_NULL:
					break;
				case VALUE_STRING:
					break;
				case VALUE_TRUE:
					break;
				default:
					break;
				}
			}

		}
		
	}
}
