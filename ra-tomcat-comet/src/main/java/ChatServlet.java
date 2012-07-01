import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ChatServlet extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = 1L;

	private final BlockingQueue<CometEvent> events = new LinkedBlockingQueue<>();

	@Override
	public void init() throws ServletException {
		super.init();
		getServletContext().setAttribute(ChatServlet.class.getName(), this);
	}

	@Override
	public void event(CometEvent evt) throws IOException, ServletException {

		HttpServletRequest request = evt.getHttpServletRequest();
		String user = (String) request.getSession().getAttribute("user");
		switch (evt.getEventType()) {
		case BEGIN: {
			if ("GET".equals(request.getMethod())) {
				evt.setTimeout(Integer.MAX_VALUE);
				events.offer(evt);
			} else {
				String message = request.getParameter("message");
				if ("/disconnect".equals(message)) {
					broadcast(user + " disconnected");
					request.getSession().removeAttribute("user");
					events.remove(evt);
				} else if (message != null) {
					broadcast("[" + user + "]" + message);
				}
				evt.close();
			}
			break;
		}
		case END:
			break;
		case ERROR:
			break;
		case READ:
			break;
		default:
			break;
		}
	}

	void broadcast(String message) throws IOException {
		Queue<CometEvent> q = new LinkedList<>();
		events.drainTo(q);
		while (!q.isEmpty()) {
			CometEvent event = q.poll();
			HttpServletResponse resp = event.getHttpServletResponse();
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.getWriter().write(message);
			event.close();
		}
	}

}
