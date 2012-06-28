package ch.rasc.pubsub.spring;

import org.springframework.context.ApplicationEvent;

public class AppEvent extends ApplicationEvent {

	private final String message;

	public AppEvent(final Object source, final String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
