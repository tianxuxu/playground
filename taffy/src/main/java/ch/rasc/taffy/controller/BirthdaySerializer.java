package ch.rasc.taffy.controller;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BirthdaySerializer extends JsonSerializer<LocalDate> {

	private final static DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormat
			.forPattern("MM-dd-yyyy");

	@Override
	public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(BIRTHDAY_FORMATTER.print(value));
	}
}
