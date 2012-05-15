package ch.rasc.pubsub.guava;

public class SpecialMsgEvent extends MsgEvent {

	private String user;

	public SpecialMsgEvent(String message, String user) {
		super(message);
		this.user = user;
	}

	public String getUser() {
		return user;
	}

}
