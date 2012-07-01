package ch.rasc.pubsub.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);

		ctx.getBean(EventPublisher.class).publishEvent("hello world");

	}

}
