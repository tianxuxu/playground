package ch.rasc.jsonp;

import java.util.Collections;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class StreamWrite {
	public static void main(String[] args) {
		Map<String, Boolean> properties = Collections
				.singletonMap(JsonGenerator.PRETTY_PRINTING, Boolean.FALSE);
		try (JsonGenerator jg = Json.createGeneratorFactory(properties)
				.createGenerator(System.out)) {

			// @formatter:off
			jg.writeStartObject().write("name", "Jane Doe").writeStartObject("address")
					.write("type", 1).write("street", "1 A Street").writeNull("city")
					.write("verified", false).writeEnd().writeStartArray("phone-numbers")
					.writeStartObject().write("number", "555-1111")
					.write("extension", "123").writeEnd().writeStartObject()
					.write("number", "555-2222").writeNull("extension").writeEnd()
					.writeEnd().writeEnd();
			// @formatter:on

		}
	}
}
