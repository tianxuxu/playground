package ch.rasc.mongodb.geolite;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportFile {
	public static void main(final String[] args) throws IOException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("ch.rasc.mongodb.geolite");

		GeoliteImporter geoliteImporter = ctx.getBean("geoliteImporter", GeoliteImporter.class);
		geoliteImporter.importData();
	}
}
