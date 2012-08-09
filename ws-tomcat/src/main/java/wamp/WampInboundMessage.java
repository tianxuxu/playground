package wamp;


public abstract class WampInboundMessage extends WampMessage {

	public WampInboundMessage(MessageType type) {
		super(type);
	}

	public abstract Object[] serialize();

}
