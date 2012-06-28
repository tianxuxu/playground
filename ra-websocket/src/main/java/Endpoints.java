import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.WebSocket;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class Endpoints {
	private final Queue<Endpoint> endpoints = new ConcurrentLinkedQueue<Endpoint>();

	void broadcast(final String data) {
		for (Endpoint endpoint : endpoints) {
			endpoint.send(data);
		}
	}

	void offer(final Endpoint endpoint) {
		endpoints.offer(endpoint);
	}

	void remove(final Endpoint endpoint) {
		endpoints.remove(endpoint);
	}

	public WebSocket newEndpoint() {
		return new Endpoint(this);
	}
}
