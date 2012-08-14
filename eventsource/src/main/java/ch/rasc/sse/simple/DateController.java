package ch.rasc.sse.simple;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DateController {

	private final static AtomicInteger counter = new AtomicInteger(1);
	
	@RequestMapping(value = "/date", method = RequestMethod.GET, produces = "text/event-stream")
	@ResponseBody
	public String getTime(@RequestHeader(value="Last-Event-ID", required=false) String lastEventId) {
		System.out.println("Last Event Id: " + lastEventId);
		
		StringBuilder sb = new StringBuilder();
		sb.append("data:");
		sb.append(DateTime.now().toString("dd.MM.yyyy HH:mm:ss.SSS"));
		sb.append("\n");
		
		sb.append("id:");
		sb.append(counter.incrementAndGet());
		sb.append("\n");
		
		sb.append("retry:");
		sb.append((ThreadLocalRandom.current().nextInt(10)+3)*1000);
		sb.append("\n\n");
		
		System.out.println(sb);
		
		return sb.toString();
	}

}
