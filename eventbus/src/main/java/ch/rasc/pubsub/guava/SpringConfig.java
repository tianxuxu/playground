package ch.rasc.pubsub.guava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.pubsub.guava" })
public class SpringConfig {

	@Bean
	public EventBus eventBus() {
		EventBus eventBus = new EventBus();
		return eventBus;

		//AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));
		//return asyncEventBus;
	}

}
