package ch.rasc.reactorsandbox.samples;

import static reactor.event.selector.Selectors.$;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.event.Event;

/**
 * @author Jon Brisbin
 */
public class ReactorSamples {

	public static void main(String... args) {
		Environment env = new Environment();
		Reactor r = Reactors.reactor().env(env).dispatcher("ringBuffer").get();

		// Subscribe to topic "test"
		r.<Event<String>> on($("test"),
				ev -> System.out.println("hi " + ev.getData()));

		// Notify topic "test"
		r.notify("test", Event.wrap("Jon"));

		// Subscribe to topic "test2" and reply with value
		r.receive($("test2"), event -> "Jon");

		// Notify topic "test2" and reply to topic "test"
		r.send("test2", Event.wrap("test2").setReplyTo("test"));

		env.shutdown();
	}

}
