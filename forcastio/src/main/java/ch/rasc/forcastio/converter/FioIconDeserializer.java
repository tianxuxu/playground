package ch.rasc.forcastio.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ch.rasc.forcastio.model.FioIcon;

public class FioIconDeserializer extends JsonDeserializer<FioIcon> {

	@Override
	public FioIcon deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return FioIcon.findByJsonValue(jp.getText());
	}

}