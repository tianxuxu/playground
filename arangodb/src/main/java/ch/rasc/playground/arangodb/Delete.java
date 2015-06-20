package ch.rasc.playground.arangodb;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentEntity;

public class Delete {
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
			arangoDriver.deleteDocument(myDocument.getDocumentHandle());
		}
		catch (ArangoException e) {
			System.out.println("Failed to delete document. " + e.getMessage());
		}

	}
}
