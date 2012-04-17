package ch.rasc.eventbus.hazelcast;

import java.io.Serializable;

public class ShortMessageEvent implements Serializable {

	private String msg;

	public ShortMessageEvent(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
