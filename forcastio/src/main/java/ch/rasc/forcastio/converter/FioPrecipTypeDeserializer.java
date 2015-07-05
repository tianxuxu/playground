package ch.rasc.forcastio.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ch.rasc.forcastio.model.FioPrecipType;

public class FioPrecipTypeDeserializer extends JsonDeserializer<FioPrecipType> {

	@Override
	public FioPrecipType deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return FioPrecipType.findByJsonValue(jp.getText());
	}

}