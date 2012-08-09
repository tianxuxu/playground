package wamp.out;

import wamp.MessageType;
import wamp.WampInboundMessage;

public class WampCallResultMessage extends WampInboundMessage {

	private final String callId;

	private final Object result;

	public WampCallResultMessage(String callId, Object result) {
		super(MessageType.CALL);
		this.callId = callId;
		this.result = result;
	}

	@Override
	public Object[] serialize() {
		return new Object[] { getType(), callId, result };
	}

}
