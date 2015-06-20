package ch.rasc.playground.arangodb;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;

public class Write {
	public static void main(String[] args) {
		ArangoConfigure configure = new ArangoConfigure();
		configure.init();
		ArangoDriver arangoDriver = new ArangoDriver(configure);

		String dbName = "mydb";
		try {
			arangoDriver.createDatabase(dbName);
			System.out.println("Database created: " + dbName);
		}
		catch (Exception e) {
			System.out.println("Failed to create database " + dbName + "; "
					+ e.getMessage());
		}

		arangoDriver.setDefaultDatabase(dbName);

		String collectionName = "firstCollection";
		try {
			CollectionEntity myArangoCollection = arangoDriver
					.createCollection(collectionName);
			System.out.println("Collection created: " + myArangoCollection.getName());
		}
		catch (Exception e) {
			System.out.println("Failed to create colleciton " + collectionName + "; "
					+ e.getMessage());
		}

		BaseDocument myObject = new BaseDocument();
		myObject.setDocumentKey("myKey");
		myObject.addAttribute("a", "Foo");
		myObject.addAttribute("b", 42);
		try {
			arangoDriver.createDocument(collectionName, myObject);
			System.out.println("Document created");
		}
		catch (ArangoException e) {
			System.out.println("Failed to create document. " + e.getMessage());
		}
	}

}