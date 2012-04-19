package ch.rasc.pubsub.hazelcast;

import javax.annotation.PostConstruct;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class ShortMessageListener implements MessageListener<ShortMessageEvent> {

	@PostConstruct
	public void register() {
		Hazelcast.<ShortMessageEvent>getTopic("my_topic").addMessageListener(this);
	}

	@Override
	public void onMessage(Message<ShortMessageEvent> message) {
		System.out.println(message.getMessageObject().getMsg());
	}

}
