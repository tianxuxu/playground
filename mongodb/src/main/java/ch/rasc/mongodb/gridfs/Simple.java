package ch.rasc.mongodb.gridfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Simple {

	public static void main(final String[] args) throws MongoException, IOException {
		Simple.simpleFile();
	}

	private static void simpleFile() throws MongoException, IOException {
		Mongo mongo = new Mongo("localhost");

		DB db = mongo.getDB("testdb");
		DBCollection collection = db.getCollection("files");

		File currentDir = new File(".");
		File[] files = currentDir.listFiles();
		for (File file : files) {

			if (file.isFile()) {
				System.out.println(file.getName());
				BasicDBObject fileObject = new BasicDBObject();
				fileObject.append("fileName", file.getName());
				fileObject.append("content", FileUtils.readFileToByteArray(file));
				collection.insert(fileObject);
			}
		}

	}

}
