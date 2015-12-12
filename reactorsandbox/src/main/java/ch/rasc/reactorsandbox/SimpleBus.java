package ch.rasc.reactorsandbox;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.Processors;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.ObjectSelector;
import reactor.bus.selector.Selector;
import reactor.bus.selector.Selectors;
import reactor.core.processor.ExecutorProcessor;

public class SimpleBus {

	public static void main(String[] args) {

		ExecutorProcessor<Event<?>, Event<?>> processor = Processors.queue("bus");
		//ExecutorProcessor<Event<?>, Event<?>> processor = Processors.topic("bus");
		//RingBufferProcessor<Event<?>> processor = RingBufferProcessor.create();

		EventBus eventBus = EventBus.create(processor);

		Selector<String> selector = ObjectSelector.objectSelector("parse");
		eventBus.on(selector, ev -> {
			try {
				TimeUnit.SECONDS.sleep(10);
			}
			catch (Exception e) {
				//ignore this
			}
			System.out.println(ev);
			System.out.println("selector: " + Thread.currentThread());
		});

		eventBus.on(Selectors.$("test")).subscribe(new Subscriber<Event<?>>() {
			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}

			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscribe:" + s);
			}

			@Override
			public void onNext(Event<?> t) {
				System.out.println("onNext:" + t);

			}

			@Override
			public void onError(Throwable t) {
				System.out.println("onError:" + t);

			}
		});

		System.out.println(Thread.currentThread());
		// Send events to this EventBus and trigger all actions
		// that match the given Selector
		eventBus.notify("parse", Event.wrap("Hello World!"));
		eventBus.notify("parse", Event.wrap("A second Hello World!"));

		Event<String> testEvent = Event.wrap("test");
		testEvent.getHeaders().set("mime-type", "me");
		testEvent.setReplyTo("me");
		eventBus.notify("test", testEvent);

		processor.awaitAndShutdown();

	}

}
