package ch.rasc.forcastio.json;

import java.io.IOException;

import ch.rasc.forcastio.model.FioResponse;

public interface JsonConverter {
	FioResponse deserialize(String json) throws IOException;
}
