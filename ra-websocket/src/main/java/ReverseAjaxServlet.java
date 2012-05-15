import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ReverseAjaxServlet extends WebSocketServlet {

	final Endpoints endpoints = new Endpoints();

	private final Thread generator = new Thread("Event generator") {
		@Override
		public void run() {
			Random random = new Random();
			ObjectMapper mapper = new ObjectMapper();

			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(random.nextInt(5000));
					endpoints.broadcast(mapper.writeValueAsString(new String[] { "At " + new Date() }));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void init() throws ServletException {
		super.init();
		generator.start();
	}

	@Override
	public void destroy() {
		generator.interrupt();
		super.destroy();
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return endpoints.newEndpoint();
	}

}
