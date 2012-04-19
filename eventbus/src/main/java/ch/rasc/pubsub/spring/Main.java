package ch.rasc.pubsub.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.rasc.pubsub.spring.EventPublisher;

public class Main {

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ch.rasc.pubsub.spring");
		ctx.getBean(EventPublisher.class).publishEvent("hello world");
	}

}
