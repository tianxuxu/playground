package ch.rasc.mongodb.sandbox;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JsonRecord {
	private List<String> result;

	@JsonSerialize(using = CollectionSerializer.class)
	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	private final static class CollectionSerializer extends JsonSerializer<List<String>> {

		@Override
		public void serialize(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
				JsonProcessingException {

			StringBuilder sb = new StringBuilder();
			sb.append("[");
			if (value != null) {
				for (int i = 0; i < value.size(); i++) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append(value.get(i));
				}
			}
			sb.append("]");
			jgen.writeRawValue(sb.toString());

		}

	}
}
