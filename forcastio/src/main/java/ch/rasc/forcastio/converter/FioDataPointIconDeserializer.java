package ch.rasc.forcastio.converter;

import java.io.IOException;

import ch.rasc.forcastio.model.FioDataPointIcon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class FioDataPointIconDeserializer extends
		JsonDeserializer<FioDataPointIcon> {

	@Override
	public FioDataPointIcon deserialize(JsonParser jp,
			DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		return FioDataPointIcon.findByJsonValue(jp.getText());
	}

}