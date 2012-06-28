package ch.rasc.pubsub.guava;

public class MsgEvent {
	private final String message;

	public MsgEvent(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
