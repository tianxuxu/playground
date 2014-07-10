package ch.rasc.sse.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/simplelong")
public class LongPollingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lastEventId = request.getHeader("Last-Event-ID");
		int lastId = 0;
		if (lastEventId != null) {
			lastId = Integer.valueOf(lastEventId);
		}

		response.setContentType("text/event-stream");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		@SuppressWarnings("resource")
		PrintWriter out = response.getWriter();

		try {
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 30));
		}
		catch (InterruptedException e) {
			// do nothing
		}

		StringBuilder sb = new StringBuilder();

		sb.append("data:event-").append(lastId + 1).append("\n");
		sb.append("id:").append(lastId + 1).append("\n");
		sb.append("\n");

		out.write(sb.toString());
		out.flush();

	}
}
