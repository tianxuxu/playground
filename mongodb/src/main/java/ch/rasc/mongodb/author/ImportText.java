package ch.rasc.mongodb.author;

import java.io.File;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportText {
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ch.rasc.mongodb.author")) {

			TextImporter importer = ctx.getBean("textImporter", TextImporter.class);
			long start = System.currentTimeMillis();

			File dir = new File("D:\\_download\\OLD_PROJECTS\\OLD_PROJECTS\\MVS\\mvs0\\txt\\archiv");
			File[] files = dir.listFiles();
			for (File f : files) {
				importer.doImport(f);
			}

			System.out.println((System.currentTimeMillis() - start) + " ms");
		}
	}
}
