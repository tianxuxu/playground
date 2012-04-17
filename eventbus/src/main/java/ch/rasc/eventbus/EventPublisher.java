package ch.rasc.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

	private ApplicationEventPublisher publisher;

	@Autowired
	public EventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publishEvent(String message) {
		publisher.publishEvent(new AppEvent(this, message));
	}

}
