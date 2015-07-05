package ch.rasc.forcastio.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ch.rasc.forcastio.model.FioUnit;

public class FioUnitDeserializer extends JsonDeserializer<FioUnit> {

	@Override
	public FioUnit deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return FioUnit.findByJsonValue(jp.getText());
	}

}