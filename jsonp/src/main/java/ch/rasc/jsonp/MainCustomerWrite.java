package ch.rasc.jsonp;

import java.util.Collections;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

public class MainCustomerWrite {

	public static void main(String[] args) {
		Customer c = new Customer();
		c.setId(1209);
		c.setName("Limited Inc.");
		c.setCity("Little Town");

		JsonObject customerJson = Kv.json(Kv.of("id", c::getId),
				Kv.of("name", c::getName), Kv.of("city", c::getCity));

		Map<String, Boolean> properties = Collections
				.singletonMap(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
		try (JsonWriter writer = Json.createWriterFactory(properties)
				.createWriter(System.out)) {
			writer.write(customerJson);
		}
	}

}
