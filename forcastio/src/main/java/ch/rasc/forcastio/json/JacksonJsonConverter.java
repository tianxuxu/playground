package ch.rasc.forcastio.json;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.forcastio.model.FioResponse;

public class JacksonJsonConverter implements JsonConverter {

	private final ObjectMapper objectMapper;
	
	public JacksonJsonConverter() {
		this(new ObjectMapper());
	}
	
	public JacksonJsonConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public FioResponse deserialize(String json) throws IOException {
		return this.objectMapper.readValue(json, FioResponse.class);
	}

}
