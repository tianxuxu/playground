package ch.rasc.eventbus.guava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.eventbus.guava" })
public class SpringConfig {

	@Bean
	public EventBus eventBus() {
		EventBus eventBus = new EventBus();
		return eventBus;
	}

}
