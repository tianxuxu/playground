package ch.rasc.springwebsocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class RegistryHandler extends TextWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		sessions.remove(session.getId());
	}

	@Async
	public void sendToAll(String message) {
		TextMessage textMessage = new TextMessage(message);

		for (WebSocketSession session : sessions.values()) {
			if (session.isOpen()) {
				try {
					session.sendMessage(textMessage);
				}
				catch (IOException e) {
					sessions.remove(session.getId());
					e.printStackTrace();
				}
			}
			else {
				sessions.remove(session.getId());
			}
		}
	}

}