package ch.rasc.pubsub.guava;

public class SpecialMsgEvent extends MsgEvent {

	private final String user;

	public SpecialMsgEvent(final String message, final String user) {
		super(message);
		this.user = user;
	}

	public String getUser() {
		return user;
	}

}
