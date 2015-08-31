package ch.rasc.mongodb.gridfs;

import java.io.File;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class Big {

	public static void main(String[] args) throws MongoException, IOException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) throws IOException {
		@SuppressWarnings("deprecation")
		DB db = mongo.getDB("testdb");

		GridFS gfs = new GridFS(db);

		File currentDir = new File(".");
		File[] files = currentDir.listFiles();
		for (File file : files) {

			if (file.isFile()) {

				GridFSInputFile gfi = gfs.createFile(file);				
				gfi.save();
			}
		}

	}

}
