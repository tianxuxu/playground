package ch.rasc.reactorsandbox.samples;

import static reactor.bus.selector.Selectors.$;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.fn.Consumer;
import reactor.rx.Promise;
import reactor.rx.Streams;
import reactor.rx.stream.Broadcaster;
import reactor.spring.context.config.EnableReactor;

/**
 * @author Jon Brisbin
 * @author Stephane Maldini
 */
@EnableAutoConfiguration
public class DispatcherSamples implements CommandLineRunner {

	@Autowired
	private Consumer<Event<EventBus>> consumer;
	@Autowired
	private Environment env;
	@Autowired
	private EventBus threadPoolReactor;

	@Override
	public void run(String... args) throws Exception {
		threadPoolDispatcher();
		multipleRingBufferDispatchers();

		this.env.shutdown();
	}

	private void threadPoolDispatcher() {
		// Bind to a Selector using an anonymous object
		Selector anon = $();

		Broadcaster<Object> broadcaster = Streams.broadcast();
		Promise<List<Object>> promise = broadcaster.toList(3);

		this.threadPoolReactor.on(anon, this.consumer);

		this.threadPoolReactor.notify(anon.getObject(),
				Event.wrap(this.threadPoolReactor));
		this.threadPoolReactor.notify(anon.getObject(),
				Event.wrap(this.threadPoolReactor));
		this.threadPoolReactor.notify(anon.getObject(),
				Event.wrap(this.threadPoolReactor));

		promise.poll();
	}

	private void multipleRingBufferDispatchers() {
		Broadcaster<Object> broadcaster = Streams.broadcast();
		Promise<List<Object>> promise = broadcaster.toList(5);

		EventBus r1 = EventBus.config().env(this.env).dispatcher(Environment.SHARED)
				.get();
		EventBus r2 = EventBus.config().env(this.env).dispatcher(Environment.SHARED)
				.get();

		// Bind to a Selector using an anonymous object
		Selector anon = $();

		r1.on(anon, this.consumer);
		r2.on(anon, this.consumer);

		r1.notify(anon.getObject(), Event.wrap(r1));
		r1.notify(anon.getObject(), Event.wrap(r1));
		r1.notify(anon.getObject(), Event.wrap(r1));

		r2.notify(anon.getObject(), Event.wrap(r2));
		r2.notify(anon.getObject(), Event.wrap(r2));

		promise.poll();
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
		public Consumer<Event<EventBus>> consumer(Logger log) {
			return ev -> log.info("Triggered by anonymous object in thread {} on {}",
					Thread.currentThread(), ev.getData());
		}

		@Bean
		public EventBus threadPoolReactor(Environment env) {
			return EventBus.config().env(env).dispatcher(Environment.THREAD_POOL).get();
		}

	}

}
