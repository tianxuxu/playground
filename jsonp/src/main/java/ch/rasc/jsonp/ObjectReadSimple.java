package ch.rasc.jsonp;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

public class ObjectReadSimple {

	public static void main(String[] args) {

		String json = "{\"lastName\": \"Doe\", \"firstName\": \"John\", \"hobbies\": [\"Reading books\", \"Singing\"]}";

		try (JsonReader reader = Json.createReader(new StringReader(json))) {
			JsonObject jsonObject = reader.readObject();

			String firstName = jsonObject.getString("firstName");
			String lastName = jsonObject.getString("lastName");
			JsonArray array = jsonObject.getJsonArray("hobbies");

			System.out.println(firstName); // John
			System.out.println(lastName); // Doe

			for (JsonValue value : array) {
				System.out.println(((JsonString) value).getString());
			}
		}
	}
}
