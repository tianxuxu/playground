package wamp;

public class WampMessage {

	private MessageType type;

	public WampMessage(MessageType type) {
		this.type = type;
	}

	public int getType() {
		return type.getType();
	}

}
