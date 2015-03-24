package ch.rasc.reactorsandbox.samples;

import static reactor.bus.selector.Selectors.$;

import java.util.concurrent.TimeUnit;

import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.core.dispatch.SynchronousDispatcher;

/**
 * @author Jon Brisbin
 * @author Stephane Maldini
 */
public class EventBusSamples {

	public static void main(String... args) throws InterruptedException {
		// Environment env = new Environment();
		// EventBus r = EventBus.config().env(env).dispatcher("ringBuffer").get();
		//
		// // Subscribe to topic "test"
		// r.<Event<String>> on($("test"), ev -> System.out.println("hi " +
		// ev.getData()));
		//
		// // Notify topic "test"
		// r.notify("test", Event.wrap("Jon"));
		//
		// // Subscribe to topic "test2" and reply with value
		// r.receive($("test2"), event -> "Jon");
		//
		// // Notify topic "test2" and reply to topic "test"
		// r.send("test2", Event.wrap("test2").setReplyTo("test"));
		//
		// env.shutdown();
		//
		//
		Environment.initialize();
		System.out.println(Thread.currentThread());
		EventBus bus = EventBus.create(Environment.cachedDispatcher());
		//EventBus bus = EventBus.create(Environment.workDispatcher());
		//EventBus bus = EventBus.create(Environment.sharedDispatcher());
		//EventBus bus = EventBus.create(new SynchronousDispatcher());
		
		bus.on($("topic"), (Event<String> ev) -> {
			String s = ev.getData();
			System.out.printf("Got %s on thread %s%n", s, Thread.currentThread());
			try {
				TimeUnit.MILLISECONDS.sleep(10000);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});

		for (int i = 0; i < 100; i++) {
			bus.notify("topic", Event.wrap("Hello World!"));
		}

		TimeUnit.SECONDS.sleep(10000);

	}

}
