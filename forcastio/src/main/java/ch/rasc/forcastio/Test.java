package ch.rasc.forcastio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import ch.rasc.forcastio.model.FioResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {

		System.out.println("1371642537");
		System.out.println(new Date().getTime());

		ObjectMapper om = new ObjectMapper();

		FioResponse fr = null;
		try (InputStream is = Test.class
				.getResourceAsStream("/forecastio_reponse.txt");) {
			fr = om.readValue(is, FioResponse.class);
		}

		if (fr != null) {
			System.out.println(fr);
		}
	}

}
