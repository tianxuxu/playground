package ch.rasc.jsonp;

import java.util.Collections;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

public class ObjectWrite {
	public static void main(String[] args) {
		JsonObject model = Json
				.createObjectBuilder()
				.add("firstName", "Duke")
				.add("lastName", "Java")
				.add("age", 18)
				.add("streetAddress", "100 Internet Dr")
				.add("city", "JavaTown")
				.add("state", "JA")
				.add("postalCode", "12345")
				.add("phoneNumbers",
						Json.createArrayBuilder()
								.add(Json.createObjectBuilder().add("type", "mobile")
										.add("number", "111-111-1111"))
								.add(Json.createObjectBuilder().add("type", "home")
										.add("number", "222-222-2222"))).build();

		Map<String, Boolean> properties = Collections.singletonMap(
				JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
		try (JsonWriter writer = Json.createWriterFactory(properties).createWriter(
				System.out)) {
			writer.write(model);
		}
	}
}
