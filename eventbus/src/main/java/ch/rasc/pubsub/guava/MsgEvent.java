package ch.rasc.pubsub.guava;

public class MsgEvent {
	private String message;

	public MsgEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
