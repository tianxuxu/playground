package ch.rasc.reactorsandbox.samples;

import static reactor.event.selector.Selectors.$;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.event.selector.Selector;
import reactor.function.Consumer;
import reactor.function.support.Boundary;

/**
 * @author Jon Brisbin
 */
@EnableAutoConfiguration
public class DispatcherSamples implements CommandLineRunner {

	@Autowired
	private Consumer<Event<Reactor>> consumer;

	@Autowired
	private Environment env;

	@Autowired
	private Reactor threadPoolReactor;

	@Override
	public void run(String... args) throws Exception {
		threadPoolDispatcher();
		multipleEventLoopDispatchers();
		multipleRingBufferDispatchers();

		env.shutdown();
	}

	private void threadPoolDispatcher() {
		Boundary b = new Boundary();

		// Bind to a Selector using an anonymous object
		Selector anon = $();

		threadPoolReactor.on(anon, b.bind(consumer, 3));

		threadPoolReactor.notify(anon, Event.wrap(threadPoolReactor));
		threadPoolReactor.notify(anon, Event.wrap(threadPoolReactor));
		threadPoolReactor.notify(anon, Event.wrap(threadPoolReactor));

		b.await();
	}

	private void multipleEventLoopDispatchers() {
		Boundary b = new Boundary();

		Reactor r1 = Reactors.reactor().env(env).dispatcher(Environment.EVENT_LOOP).get();
		Reactor r2 = Reactors.reactor().env(env).dispatcher(Environment.EVENT_LOOP).get();

		// Bind to a Selector using an anonymous object
		Selector anon = $();

		r1.on(anon, b.bind(consumer, 3));
		r2.on(anon, b.bind(consumer, 2));

		r1.notify(anon, Event.wrap(r1));
		r1.notify(anon, Event.wrap(r1));
		r1.notify(anon, Event.wrap(r1));

		r2.notify(anon, Event.wrap(r2));
		r2.notify(anon, Event.wrap(r2));

		b.await();
	}

	private void multipleRingBufferDispatchers() {
		Boundary b = new Boundary();

		Reactor r1 = Reactors.reactor().env(env).dispatcher(Environment.RING_BUFFER).get();
		Reactor r2 = Reactors.reactor().env(env).dispatcher(Environment.RING_BUFFER).get();

		// Bind to a Selector using an anonymous object
		Selector anon = $();

		r1.on(anon, b.bind(consumer, 3));
		r2.on(anon, b.bind(consumer, 2));

		r1.notify(anon, Event.wrap(r1));
		r1.notify(anon, Event.wrap(r1));
		r1.notify(anon, Event.wrap(r1));

		r2.notify(anon, Event.wrap(r2));
		r2.notify(anon, Event.wrap(r2));

		b.await();
	}

	public static void main(String... args) {
		SpringApplication.run(DispatcherSamples.class, args);
	}

	@Configuration
	@EnableReactor
	static class ReactorConfiguration {

		@Bean
		public Logger log() {
			return LoggerFactory.getLogger(DispatcherSamples.class);
		}

		@Bean
		public Consumer<Event<Reactor>> consumer(final Logger log) {
			return new Consumer<Event<Reactor>>() {
				@Override
				public void accept(Event<Reactor> ev) {
					log.info("Triggered by anonymous object in thread {} on {}", Thread.currentThread(), ev.getData());

				}
			};
		}

		@Bean
		public Reactor threadPoolReactor(Environment env) {
			return Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();
		}

	}

}
