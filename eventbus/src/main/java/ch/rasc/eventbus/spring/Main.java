package ch.rasc.eventbus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.rasc.eventbus.spring.EventPublisher;

public class Main {

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ch.rasc.eventbus.spring");
		ctx.getBean(EventPublisher.class).publishEvent("hello world");
	}

}
