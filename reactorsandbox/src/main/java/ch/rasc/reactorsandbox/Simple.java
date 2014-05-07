package ch.rasc.reactorsandbox;

import java.util.concurrent.TimeUnit;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.event.selector.ObjectSelector;
import reactor.event.selector.Selector;

public class Simple {

	public static void main(String[] args) throws InterruptedException {

		Environment env = new Environment();

		Reactor reactor = Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();

		Selector selector = ObjectSelector.objectSelector("parse");
		reactor.on(selector, ev -> System.out.println(ev));

		// Send an event to this Reactor and trigger all actions
		// that match the given Selector
		reactor.notify("parse", Event.wrap("Hello World!"));
		reactor.notify("test", Event.wrap("test"));
		TimeUnit.SECONDS.sleep(20);

	}

}
