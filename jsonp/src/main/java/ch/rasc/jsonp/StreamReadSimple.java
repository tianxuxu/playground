package ch.rasc.jsonp;

import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class StreamReadSimple {

	public static void main(String[] args) {
		String json = "{\"lastName\": \"Doe\", \"firstName\": \"John\", \"hobbies\": [\"Reading books\", \"Singing\"]}";

		try (JsonParser parser = Json.createParser(new StringReader(json))) {
			while (parser.hasNext()) {
				Event e = parser.next();
				System.out.println(e);
			}
		}

	}

}
