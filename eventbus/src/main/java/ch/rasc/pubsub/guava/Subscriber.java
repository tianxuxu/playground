package ch.rasc.pubsub.guava;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

@Service
public class Subscriber {

	@Subscribe
	public void handleMsgEvent(final MsgEvent event) {
		System.out.println("MsgEvent: " + event.getMessage());
	}

	@Subscribe
	public void handleSpecialMsgEvent(final SpecialMsgEvent event) {
		System.out.println("SpecialMsgEvent: " + event.getMessage() + " User: " + event.getUser());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Subscribe
	public void handleDeadEvents(final DeadEvent deadEvent) {
		System.out.print("Event without a subscriber: ");
		System.out.println(deadEvent.getEvent());
	}

}
