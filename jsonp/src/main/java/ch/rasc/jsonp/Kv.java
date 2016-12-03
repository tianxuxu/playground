package ch.rasc.jsonp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Supplier;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Kv {
	private Supplier<?> getter;
	private String key;

	public static Kv of(String key, Supplier<?> getter) {
		Kv kv = new Kv();
		kv.key = key;
		kv.getter = getter;
		return kv;
	}

	public Supplier<?> getGetter() {
		return getter;
	}

	public String getKey() {
		return key;
	}

	public static JsonObject json(Kv... keyValues) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		if (keyValues != null) {
			for (Kv kv : keyValues) {
				Object object = kv.getGetter().get();

				if (object == null) {
					builder.addNull(kv.getKey());
				}
				else if (object instanceof BigDecimal) {
					builder.add(kv.getKey(), (BigDecimal)object);
				}
				else if (object instanceof BigInteger) {
					builder.add(kv.getKey(), (BigInteger)object);
				}
				else if (object instanceof Boolean) {
					builder.add(kv.getKey(), (Boolean)object);
				}
				else if (object instanceof Double) {
					builder.add(kv.getKey(), (Double)object);
				}
				else if (object instanceof Integer) {
					builder.add(kv.getKey(), (Integer)object);
				}
				else if (object instanceof Long) {
					builder.add(kv.getKey(), (Long)object);
				}
				else if (object instanceof String) {
					builder.add(kv.getKey(), (String)object);
				}
				else {
					builder.add(kv.getKey(), String.valueOf(object));
				}
			}
		}
		return builder.build();
	}
	
	/*
	public List<Customer> readCb() {
		
		ObjectMapper om = new ObjectMapper();
		ArrayNode an = om.createArrayNode();
		StreamSupport
				.stream(this.mongoDb.getCollection(Customer.class).find()
						.projection(Projections.include(CCustomer.id, CCustomer.name))
						.sort(Sorts.ascending(CCustomer.name)).spliterator(), false)
				.map(cust -> {
					return json(Kv.of("id", cust::getId), Kv.of("name", cust::getName));
				}).forEach(an::add);
		return an;
	}
	
	private static ObjectNode json(Kv... keyValues) {
		ObjectMapper om = new ObjectMapper();
		ObjectNode on = om.createObjectNode();
		if (keyValues != null) {
			for (Kv kv : keyValues) {
				Object object = kv.getGetter().get();
				if (object instanceof String) {
					on.put(kv.getKey(), (String)object);
				}
			}
		}
		return on;
	}
	 */
}