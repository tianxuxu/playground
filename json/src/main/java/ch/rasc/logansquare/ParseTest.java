package ch.rasc.logansquare;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseTest {

	public static void main(String[] args) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		try (InputStream is = ParseTest.class
				.getResourceAsStream("/testdata/tinysample.json")) {
			String large = StreamUtils.copyToString(is, StandardCharsets.UTF_8);

			Response response = objectMapper.readValue(large, Response.class);
			response = LoganSquare.parse(large, Response.class);

			StopWatch sw = new StopWatch();
			sw.start("Jackson");
			response = objectMapper.readValue(large, Response.class);
			sw.stop();
			System.out.println(response.status);

			sw.start("LoganSquare");
			response = LoganSquare.parse(large, Response.class);
			sw.stop();
			System.out.println(response.status);

			System.out.println(sw.prettyPrint());

		}

	}

}
