package ch.rasc.pubsub.hazelcast;

import java.io.Serializable;

public class ShortMessageEvent implements Serializable {

	private String msg;

	public ShortMessageEvent(final String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

}
