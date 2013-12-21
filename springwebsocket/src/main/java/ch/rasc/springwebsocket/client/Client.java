package ch.rasc.springwebsocket.client;

import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class Client {

	public static void main(String[] args) {
		// WebSocketClient wsClient = new JettyWebSocketClient();
		WebSocketClient wsClient = new StandardWebSocketClient();

		ClientWebSocketHandler handler = new ClientWebSocketHandler();

		WebSocketConnectionManager manager = new WebSocketConnectionManager(wsClient, handler,
				"ws://localhost:8080/dispatcher/endpoint");
		manager.start();

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// do nothing
		}

		manager.stop();
	}

}
