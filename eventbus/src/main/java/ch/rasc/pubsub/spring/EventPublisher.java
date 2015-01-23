package ch.rasc.pubsub.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

	private final ApplicationEventPublisher publisher;

	@Autowired
	public EventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publishEvent(String message) {
		this.publisher.publishEvent(new AppEvent(this, message));
	}

}
