package ch.rasc.eventbus;

public class WatchEvent {
	private String msg;

	public WatchEvent(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "WatchEvent [msg=" + msg + "]";
	}

}
