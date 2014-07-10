package ch.rasc.jsonp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ObjectRead {

	public static void main(String[] args) throws ClientProtocolException, IOException {

		String apiKey = args[0];
		String latitude = "46.947922";
		String longitude = "7.444608";

		String urlTemplate = "https://api.forecast.io/forecast/%s/%s,%s?units=si&exclude=daily";
		String url = String.format(urlTemplate, apiKey, latitude, longitude);

		HttpGet httpget = new HttpGet(url);
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(httpget);
				JsonReader reader = Json.createReader(response.getEntity().getContent())) {

			JsonObject root = reader.readObject();
			JsonObject hourly = root.getJsonObject("hourly");
			JsonArray data = hourly.getJsonArray("data");
			for (JsonValue entry : data) {
				JsonObject dataBlock = (JsonObject) entry;

				long seconds = dataBlock.getJsonNumber("time").longValue();
				Calendar time = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
				time.setTimeInMillis(seconds * 1000);

				BigDecimal temperature = dataBlock.getJsonNumber("temperature")
						.bigDecimalValue();
				System.out.println(time.getTime() + " : " + temperature);
			}

		}

	}
}
