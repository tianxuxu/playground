import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import wamp.out.WampCallResultMessage;

public class M {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		U u = new U();
		u.name = "name";
		u.firstName = "firstName";
		u.email = "email";
		
		WampCallResultMessage msg = new WampCallResultMessage("callid", u);

		System.out.println(mapper.writeValueAsString(msg.serialize()));

	}

}
