package ch.ralscha.mongodbplayground.geolite;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportFile {
	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("ch.ralscha.mongodbplayground.geolite");

		GeoliteImporter geoliteImporter = ctx.getBean("geoliteImporter", GeoliteImporter.class);
		geoliteImporter.importData();
	}
}
