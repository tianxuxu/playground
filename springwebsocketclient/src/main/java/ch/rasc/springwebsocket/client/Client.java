package ch.rasc.springwebsocket.client;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Configuration
public class Client {

	@Bean
	public WebSocketClient webSocketClient() {
		return new StandardWebSocketClient();
		// return new JettyWebSocketClient();
	}

	@Bean
	public SockJsClient sockJsClient(WebSocketClient webSocketClient) {
		return new SockJsClient(Collections.singletonList(new WebSocketTransport(
				webSocketClient)));
	}

	@Bean
	public ClientQuoteHandler clientQuoteHandler() {
		return new ClientQuoteHandler();
	}

	@Bean
	public ClientMemoryHandler clientMemoryHandler() {
		return new ClientMemoryHandler();
	}

	@Bean
	public WebSocketConnectionManager quotesConnectionManager(
			WebSocketClient webSocketClient, ClientQuoteHandler clientQuoteHandler) {
		WebSocketConnectionManager manager = new WebSocketConnectionManager(
				webSocketClient, clientQuoteHandler, "ws://localhost:8080/quotes");
		manager.setAutoStartup(true);
		return manager;
	}

	@Bean
	public WebSocketConnectionManager memoryConnectionManager(SockJsClient sockJsClient,
			ClientMemoryHandler clientMemoryHandler) {

		WebSocketConnectionManager manager = new WebSocketConnectionManager(sockJsClient,
				clientMemoryHandler, "ws://localhost:8080/registry");
		manager.setAutoStartup(true);
		return manager;
	}

	public static void main(String[] args) throws InterruptedException {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				Client.class)) {
			TimeUnit.MINUTES.sleep(2);
		}
	}

}
