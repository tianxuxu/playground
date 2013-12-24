package ch.rasc.springwebsocket.client;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class Client {

	@Bean
	public WebSocketClient webSocketClient() {
		return new StandardWebSocketClient();
		//return new JettyWebSocketClient();
	}
	
	@Bean
	public ClientWebSocketHandler clientWebSocketHandler() {
		return new ClientWebSocketHandler();
	}
	
	@Bean
	public WebSocketConnectionManager webSocketConnectionManager(WebSocketClient webSocketClient, WebSocketHandler webSocketHandler) {
		WebSocketConnectionManager manager = new WebSocketConnectionManager(webSocketClient, webSocketHandler, "ws://localhost:8080/dispatcher/quotes");
		manager.setAutoStartup(true);
		return manager;
	}
	
	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Client.class);
		TimeUnit.MINUTES.sleep(1);
	}

}
