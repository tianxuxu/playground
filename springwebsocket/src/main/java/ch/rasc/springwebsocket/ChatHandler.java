package ch.rasc.springwebsocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ChatHandler extends RegistryHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		sendToAll(session.getId() + " says: <strong>" + message.getPayload() + "</strong>");
	}

}