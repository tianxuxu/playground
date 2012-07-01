import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Endpoint implements OnTextMessage {

	private final Endpoints endpoints;

	private final String user;

	private Connection connection;

	Endpoint(Endpoints endpoints, String user) {
		this.endpoints = endpoints;
		this.user = user;
	}

	@Override
	public void onOpen(Connection conn) {
		this.connection = conn;
		endpoints.broadcast(user + " connected !");
	}

	@Override
	public void onMessage(String data) {
		if ("/disconnect".equals(data)) {
			endpoints.broadcast(user + " disconnected !");
			connection.close();
		} else {
			endpoints.broadcast("[" + user + "] " + data);
		}
	}

	@Override
	public void onClose(int closeCode, String message) {
		endpoints.remove(this);
		connection = null;
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

	public String user() {
		return user;
	}
}
