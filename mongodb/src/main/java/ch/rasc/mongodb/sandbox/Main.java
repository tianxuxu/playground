package ch.rasc.mongodb.sandbox;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public class Main {

	public static void main(String[] args) throws MongoException, JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> userData = mapper.readValue(Main.class.getResourceAsStream("users.json"), List.class);

		Mongo mongo = new Mongo("localhost");
		mongo.setWriteConcern(WriteConcern.SAFE);

		DB db = mongo.getDB("testdbs");
		// db.setWriteConcern(WriteConcern.SAFE);

		DBCollection collection = db.getCollection("testcollection");
		// collection.setWriteConcern(WriteConcern.SAFE);

		collection.drop();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		for (Map<String, Object> row : userData) {

			// replace string with a date
			String dateOfBirth = (String) row.get("dob");

			try {
				row.put("dob", df.parse(dateOfBirth));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			BasicDBObject dbObject = new BasicDBObject(row);
			collection.insert(dbObject);
		}

		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject dbo = cursor.next();
			System.out.println(dbo);
		}

		DBObject query = (DBObject) JSON.parse("{'username': 'johnd'}"); // new
																			// BasicDBObject("username",
																			// "johnd");
		BasicDBObject keys = new BasicDBObject("username", 1);
		keys.append("password", 1);
		// 1 = nur diese keys zurückliefern
		// 0 = alle keys bis auf diese zurückliefern

		cursor = collection.find(query, keys);
		while (cursor.hasNext()) {
			DBObject dbo = cursor.next();
			System.out.println(dbo);
		}

	}

}
