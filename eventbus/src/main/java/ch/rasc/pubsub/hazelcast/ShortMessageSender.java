package ch.rasc.pubsub.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.ITopic;

public class ShortMessageSender {

	public void send(String msg) {
		ITopic<ShortMessageEvent> topic = Hazelcast.getTopic("my_topic");
		topic.publish(new ShortMessageEvent(msg));
	}

}
