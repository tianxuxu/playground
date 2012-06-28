package ch.rasc.pubsub.guava;

import java.util.Date;

public class TimeEvent {
	private final Date time;

	public TimeEvent(final Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

}
