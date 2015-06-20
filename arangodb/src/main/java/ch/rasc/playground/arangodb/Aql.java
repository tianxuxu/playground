package ch.rasc.playground.arangodb;

import java.util.Iterator;
import java.util.Map;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.CursorResultSet;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;

public class Aql {
	public static void main(String[] args) {
		ArangoConfigure configure = new ArangoConfigure();
		configure.init();
		ArangoDriver arangoDriver = new ArangoDriver(configure);

		String dbName = "mydb";
		arangoDriver.setDefaultDatabase(dbName);

		String collectionName = "firstCollection";
		try {
			for (Integer i = 0; i < 10; i++) {
				BaseDocument baseDocument = new BaseDocument();
				baseDocument.setDocumentKey(i.toString());
				baseDocument.addAttribute("name", "Homer");
				baseDocument.addAttribute("b", i + 42);
				arangoDriver.createDocument(collectionName, baseDocument);
			}
		}
		catch (ArangoException e) {
			System.out.println("Failed to create document. " + e.getMessage());
		}

		try {
			String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";
			Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
			CursorResultSet<BaseDocument> rs = arangoDriver.executeQueryWithResultSet(
					query, bindVars, BaseDocument.class, true, 20);

			Iterator<BaseDocument> iterator = rs.iterator();
			while (iterator.hasNext()) {
				BaseDocument aDocument = iterator.next();
				System.out.println("Key: " + aDocument.getDocumentKey());
			}
		}
		catch (ArangoException e) {
			System.out.println("Failed to execute query. " + e.getMessage());
		}

		try {
			String query = "FOR t IN firstCollection FILTER t.name == @name "
					+ "REMOVE t IN firstCollection LET removed = OLD RETURN removed";
			Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
			CursorResultSet<BaseDocument> rs = arangoDriver.executeQueryWithResultSet(
					query, bindVars, BaseDocument.class, true, 20);

			Iterator<BaseDocument> iterator = rs.iterator();
			while (iterator.hasNext()) {
				BaseDocument aDocument = iterator.next();
				System.out.println("Removed document: " + aDocument.getDocumentKey());
			}

		}
		catch (ArangoException e) {
			System.out.println("Failed to execute query. " + e.getMessage());
		}

	}

}