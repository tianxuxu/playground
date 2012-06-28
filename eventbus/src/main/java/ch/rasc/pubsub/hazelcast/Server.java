package ch.rasc.pubsub.hazelcast;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Server {

	@Bean
	public ShortMessageListener shortMessageListener() {
		return new ShortMessageListener();
	}

	@SuppressWarnings("unused")
	public static void main(final String[] args) {
		new AnnotationConfigApplicationContext(Server.class);
	}

}
