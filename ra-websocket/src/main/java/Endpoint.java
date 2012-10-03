import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Endpoint implements OnTextMessage {

	private static int clientCounter = 0;

	private Connection connection;

	private final Endpoints endpoints;

	private final int clientId = clientCounter++;

	private final ObjectMapper mapper = new ObjectMapper();

	Endpoint(Endpoints endpoints) {
		this.endpoints = endpoints;
	}

	@Override
	public void onOpen(Connection conn) {
		System.out.println("onOpen");
		this.connection = conn;
		try {
			send(mapper.writeValueAsString(new String[] { "ClientID = " + clientId }));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		endpoints.offer(this);
	}

	public void send(String data) {
		try {
			if (connection != null && connection.isOpen()) {
				connection.sendMessage(data);
			}
		} catch (IOException e) {
			connection.close();
		}
	}

	@Override
	public void onClose(int closeCode, String message) {
		System.out.println("onClose");
		endpoints.remove(this);
		connection = null;
	}

	@Override
	public void onMessage(String data) {
		System.out.println("onMessage");
		try {
			endpoints.broadcast(mapper.writeValueAsString(new String[] { "From " + clientId + " : " + data }));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
