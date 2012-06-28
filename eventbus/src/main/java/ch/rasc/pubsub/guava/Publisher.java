package ch.rasc.pubsub.guava;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

@Service
public class Publisher {

	private final EventBus eventBus;

	@Autowired
	public Publisher(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void publishMsgEvent(final String message) {
		eventBus.post(new MsgEvent(message));
	}

	public void publishSpecialMsgEvent(final String message, final String user) {
		eventBus.post(new SpecialMsgEvent(message, user));
	}

	public void publishTimeEvent() {
		eventBus.post(new TimeEvent(new Date()));
	}

}
