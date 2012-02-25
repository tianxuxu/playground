import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class PiggybackServlet extends HttpServlet {

	final Random random = new Random();
	final BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
	private final ObjectMapper mapper = new ObjectMapper();

	private final Thread generator = new Thread("Event generator") {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(random.nextInt(5000));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				messages.offer("At " + new Date());
			}
		}
	};

	@Override
	public void init() throws ServletException {
		generator.start();
	}

	@Override
	public void destroy() {
		generator.interrupt();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FORM POSTED !");
		List<String> locmessages = new LinkedList<String>();
		this.messages.drainTo(locmessages);
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");

		Map<String, Object> piggy = new HashMap<String, Object>();
		piggy.put("events", locmessages);
		piggy.put("formValid", true);
		resp.getWriter().write(mapper.writeValueAsString(piggy));

		resp.getWriter().flush();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> locmessages = new LinkedList<String>();
		this.messages.drainTo(locmessages);
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		resp.getWriter().write(mapper.writeValueAsString(locmessages));
		resp.getWriter().flush();
	}
}
