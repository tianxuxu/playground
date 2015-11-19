package ch.rasc.reactorsandbox;

import reactor.Processors;
import reactor.rx.broadcast.Broadcaster;

public class ReactorHelloWorld {
	public static void main(String... args) throws InterruptedException {

		Broadcaster<String> sink = Broadcaster.create();
		sink.dispatchOn(Processors.asyncGroup("flow")).map(f -> {
			f.toUpperCase();
			System.out.println(Thread.currentThread());
			return f;
		})
		.observe(s->{System.out.println(Thread.currentThread());})
		.consume(s -> {
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