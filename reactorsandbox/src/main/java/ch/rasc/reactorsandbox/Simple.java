package ch.rasc.reactorsandbox;

import java.util.concurrent.CountDownLatch;

import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.ObjectSelector;
import reactor.bus.selector.Selector;

public class Simple {

	public static void main(String[] args) throws InterruptedException {

		Environment env = new Environment();

		EventBus reactor = EventBus.create(env, Environment.WORK_QUEUE);

		CountDownLatch latch = new CountDownLatch(1);
		Selector<String> selector = ObjectSelector.objectSelector("parse");
		reactor.on(selector, ev -> {
			System.out.println(ev);
			System.out.println("selector: " + Thread.currentThread().getName());
			latch.countDown();
		});

		System.out.println(Thread.currentThread().getName());
		// Send an event to this Reactor and trigger all actions
		// that match the given Selector
		reactor.notify("parse", Event.wrap("Hello World!"));
		reactor.notify("test", Event.wrap("test"));

		latch.await();

	}

}
