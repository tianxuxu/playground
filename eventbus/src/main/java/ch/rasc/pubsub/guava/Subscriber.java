package ch.rasc.pubsub.guava;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

@Service
public class Subscriber {

	@Subscribe
	@AllowConcurrentEvents
	public void handleMsgEvent(MsgEvent event) {
		System.out.println("MsgEvent: " + event.getMessage());
	}

	@Subscribe
	@AllowConcurrentEvents
	public void handleSpecialMsgEvent(SpecialMsgEvent event) {
		System.out.println("SpecialMsgEvent: " + event.getMessage() + " User: " + event.getUser());
	}

	@Subscribe
	@AllowConcurrentEvents
	public void handleDeadEvents(DeadEvent deadEvent) {
		System.out.print("Event without a subscriber: ");
		System.out.println(deadEvent.getEvent());
	}

}
