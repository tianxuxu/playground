package ch.rasc.playground.arangodb;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentEntity;

public class Read {
	public static void main(String[] args) {

		ArangoConfigure configure = new ArangoConfigure();
		configure.init();
		ArangoDriver arangoDriver = new ArangoDriver(configure);

		String dbName = "mydb";
		arangoDriver.setDefaultDatabase(dbName);

		String collectionName = "firstCollection";

		DocumentEntity<BaseDocument> myDocument = null;
		BaseDocument myObject2 = null;
		try {
			myDocument = arangoDriver.getDocument(collectionName, "myKey",
					BaseDocument.class);
			myObject2 = myDocument.getEntity();
			System.out.println("Key: " + myObject2.getDocumentKey());
			System.out.println("Attribute 'a': " + myObject2.getProperties().get("a"));
			System.out.println("Attribute 'b': " + myObject2.getProperties().get("b"));
			System.out.println("Attribute 'c': " + myObject2.getProperties().get("c"));
		}
		catch (ArangoException e) {
			System.out.println("Failed to get document. " + e.getMessage());
		}

	}
}
