package ch.rasc.forcastio.converter;

import java.io.IOException;

import ch.rasc.forcastio.model.FioFlagUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class FioFlagUnitDeserializer extends JsonDeserializer<FioFlagUnit> {

	@Override
	public FioFlagUnit deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
	JsonProcessingException {
		return FioFlagUnit.findByJsonValue(jp.getText());
	}

}