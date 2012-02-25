package ch.ralscha.mongodbplayground.sandbox;

import java.util.Date;

import org.bson.types.ObjectId;

public class Main2 {

	public static void main(String[] args) {
		//		List<String> records = Lists.newArrayList();
		//		records.add("{\"name\": \"johnd\"}");
		//		records.add("{\"name\": \"bill\"}");
		//		JsonRecord jr = new JsonRecord();
		//		jr.setResult(records);
		//		
		//		ObjectMapper mapper = new ObjectMapper();
		//		String result = mapper.writeValueAsString(jr);
		//		System.out.println(result);

		Date d = new Date();
		ObjectId oid = new ObjectId(d, 0, 0);
		System.out.println(oid);
		System.out.println(new Date(oid.getTime()));

		ObjectId max = new ObjectId("4d46bf1fffffffffffffffff");
		System.out.println(new Date(max.getTime()));
		//new line
		//a second line
	}

}
