package ch.rasc.springwebsocket.client;

import org.msgpack.MessagePack;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class ClientWebSocketHandler extends BinaryWebSocketHandler {

	private final MessagePack msgpack;

	public ClientWebSocketHandler() {
		msgpack = new MessagePack();
		msgpack.register(Quote.class);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) throws Exception {
		Quote[] dst = msgpack.read(message.getPayload(), Quote[].class);
		for (Quote quote : dst) {
			System.out.println(quote);
		}

	}

}
