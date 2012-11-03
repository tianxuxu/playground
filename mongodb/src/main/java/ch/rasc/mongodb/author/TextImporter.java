package ch.rasc.mongodb.author;

import java.io.File;

public interface TextImporter {

	void doImport(String fileName);

	void doImport(File file);

}