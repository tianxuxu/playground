package ch.rasc.reactorsandbox;

import java.util.concurrent.TimeUnit;

import reactor.Fn;
import reactor.core.R;
import reactor.core.Reactor;
import reactor.fn.Consumer;
import reactor.fn.Event;
import reactor.fn.selector.BaseSelector;

public class Simple {

	public static void main(String[] args) throws InterruptedException {

		Reactor reactor = R.reactor().threadPoolExecutor().build();

		BaseSelector<String> selector = new BaseSelector<>("parse");
		reactor.on(selector, new Consumer<Event<String>>() {
			@Override
			public void accept(Event<String> ev) {
				System.out.println(ev);
			}
		});

		// Send an event to this Reactor and trigger all actions
		// that match the given Selector
		reactor.notify("parse", Fn.event("Hello World!"));
		reactor.notify("test", Fn.event("test"));
		TimeUnit.MINUTES.sleep(1);

	}

}
