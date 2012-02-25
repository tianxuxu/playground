import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ChatHandler implements AtmosphereHandler<HttpServletRequest, HttpServletResponse> {

	@Override
	public void destroy() {
		//nothing here
	}

	@Override
	public void onRequest(AtmosphereResource<HttpServletRequest, HttpServletResponse> resource) throws IOException {
		Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,
				ChatHandler.class.getName(), true);
		broadcaster.setScope(Broadcaster.SCOPE.APPLICATION);
		resource.setBroadcaster(broadcaster);
		HttpServletRequest req = resource.getRequest();
		String user = (String) req.getSession().getAttribute("user");
		if (user != null) {
			if ("GET".equals(req.getMethod())) {
				resource.suspend(-1, false);
			} else if ("POST".equals(req.getMethod())) {
				String cmd = req.getParameter("cmd");
				String message = req.getParameter("message");
				if ("disconnect".equals(cmd)) {
					close(resource);
				} else if (message != null && message.trim().length() > 0) {
					broadcaster.broadcast("[" + user + "] " + message);
				}
			}
		}
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event)
			throws IOException {
		Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,
				ChatHandler.class.getName(), true);
		// Client closed the connection.
		if (event.isCancelled()) {
			close(event.getResource());
			return;
		}
		try {
			String message = (String) event.getMessage();
			if (message != null) {
				PrintWriter writer = event.getResource().getResponse().getWriter();
				writer.write(message);
				writer.flush();
			}
		} finally {
			if (!event.isResumedOnTimeout()) {
				event.getResource().resume();
			}
		}
	}

	private void close(AtmosphereResource<HttpServletRequest, HttpServletResponse> resource) {
		resource.resume();
		Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,
				ChatHandler.class.getName(), true);
		String user = (String) resource.getRequest().getSession().getAttribute("user");
		broadcaster.broadcast(user + " disconnected");
	}
}
