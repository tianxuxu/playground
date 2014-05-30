package ch.rasc.springwebsocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class Chat4Handler extends AbstractWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	private final Executor asyncExecutor;

	public Chat4Handler(Executor asyncExecutor) {
		this.asyncExecutor = asyncExecutor;
	}

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

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		if ("get-video".equals(message.getPayload())) {
			sendImages(session);
		}
		else {
			sendToAll(message);
		}
	}

	private void sendImages(final WebSocketSession session) {
		asyncExecutor
				.execute(() -> {
					for (int index = 0; index < 49; index++) {
						byte[] imgBytes;
						try {
							ClassPathResource cp = new ClassPathResource(
									"Video/" + index + ".jpg");
							imgBytes = StreamUtils.copyToByteArray(cp
									.getInputStream());
							session.sendMessage(new BinaryMessage(imgBytes));
							TimeUnit.MILLISECONDS.sleep(150);
						}
						catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) throws Exception {
		sendToAll(message);
	}

	public void sendToAll(final WebSocketMessage<?> message) {
		asyncExecutor.execute(() -> {
			for (WebSocketSession session : sessions.values()) {
				if (session.isOpen()) {
					try {
						session.sendMessage(message);
					}
					catch (IOException e) {
						// sessions.remove(session.getId());
				e.printStackTrace();
			}
		}
		else {
			sessions.remove(session.getId());
		}
	}
})		;
	}

}