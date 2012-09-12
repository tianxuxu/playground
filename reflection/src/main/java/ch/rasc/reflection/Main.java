package ch.rasc.reflection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	public static void main(String... args) throws Exception {

		System.out.println(ModelGenerator.generateJavascript(User.class, OutputFormat.EXTJS));

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(new User()));
	}

}
