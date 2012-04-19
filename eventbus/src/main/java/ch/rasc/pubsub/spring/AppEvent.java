package ch.rasc.pubsub.spring;

import org.springframework.context.ApplicationEvent;

public class AppEvent extends ApplicationEvent {

	private String message;

	public AppEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
