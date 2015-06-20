package ch.rasc.playground.arangodb;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentEntity;

public class Update {
	public static void main(String[] args) {

		ArangoConfigure configure = new ArangoConfigure();
		configure.init();
		ArangoDriver arangoDriver = new ArangoDriver(configure);

		String dbName = "mydb";
		arangoDriver.setDefaultDatabase(dbName);

		try {
			String collectionName = "firstCollection";
			DocumentEntity<BaseDocument> myDocument = null;
			myDocument = arangoDriver.getDocument(collectionName, "myKey",
					BaseDocument.class);
			BaseDocument myObject2 = myDocument.getEntity();
			myObject2.addAttribute("c", "Bar");
			arangoDriver.updateDocument(myDocument.getDocumentHandle(), myObject2);
		}
		catch (ArangoException e) {
			System.out.println("Failed to update document. " + e.getMessage());
		}
	}
}
