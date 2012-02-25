import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ChatServlet extends WebSocketServlet {
	private final Endpoints endpoints = new Endpoints();

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return endpoints.newEndpoint(request.getParameter("user"));
	}
}
