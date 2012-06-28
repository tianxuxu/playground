import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Endpoint implements OnTextMessage {

	private final Endpoints endpoints;

	private final String user;

	private Connection connection;

	Endpoint(final Endpoints endpoints, final String user) {
		this.endpoints = endpoints;
		this.user = user;
	}

	@Override
	public void onOpen(final Connection conn) {
		this.connection = conn;
		endpoints.broadcast(user + " connected !");
	}

	@Override
	public void onMessage(final String data) {
		if ("/disconnect".equals(data)) {
			endpoints.broadcast(user + " disconnected !");
			connection.disconnect();
		} else {
			endpoints.broadcast("[" + user + "] " + data);
		}
	}

	@Override
	public void onClose(final int closeCode, final String message) {
		endpoints.remove(this);
		connection = null;
	}

	public void send(final String data) {
		try {
			if (connection != null && connection.isOpen()) {
				connection.sendMessage(data);
			}
		} catch (IOException e) {
			connection.disconnect();
		}
	}

	public String user() {
		return user;
	}
}
