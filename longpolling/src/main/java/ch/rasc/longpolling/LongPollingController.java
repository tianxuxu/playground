package ch.rasc.longpolling;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LongPollingController {

	private final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

	private List<DeferredResult<String>> results = new ArrayList<>();

	@RequestMapping("/event")
	@ResponseBody
	public DeferredResult<String> handle() {
		System.out.println("CONNECTED");
		DeferredResult<String> result = new DeferredResult<>();
		result.onCompletion(()->{System.out.println("on completion");});
		results.add(result);
		return result;
	}

	@Scheduled(fixedDelay = 20_000)
	public void update() {
		System.out.println("running udpate");
		ListIterator<DeferredResult<String>> it = results.listIterator();
		while (it.hasNext()) {
			
			try {
				DeferredResult<String> next = it.next();
				System.out.println("LOOP: " + next);
				boolean handled = next.setResult("ok");
				if (handled) {
					System.out.println("OK");
				}
				else {
					System.out.println("NOT HANDLED");
				}
			}
			catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			it.remove();
		}
	}
}
