package ch.rasc.reactorsandbox;

import reactor.core.publisher.SchedulerGroup;
import reactor.rx.Broadcaster;

public class ReactorHelloWorld {
	public static void main(String... args) throws InterruptedException {

		Broadcaster<String> sink = Broadcaster.create();
		sink.dispatchOn(SchedulerGroup.async()).map(f -> {
			f.toUpperCase();
			System.out.println(Thread.currentThread());
			return f;
		}).consume(s -> {
			System.out.printf("s=%s%n", s);
			System.out.println(Thread.currentThread());
		});

		System.out.println(Thread.currentThread());
		sink.onNext("Hello");
		sink.onNext("World");
		sink.onNext("!");

		Thread.sleep(500);
	}

}