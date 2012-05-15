package ch.rasc.taffy.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BirthdaySerializer extends JsonSerializer<LocalDate> {

	private final static DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormat.forPattern("MM-dd-yyyy");

	@Override
	public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(BIRTHDAY_FORMATTER.print(value));
	}
}
