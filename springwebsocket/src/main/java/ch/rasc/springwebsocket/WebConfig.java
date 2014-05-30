package ch.rasc.springwebsocket;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer, AsyncConfigurer {

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(simpleHandler(), "/endpoint");
		registry.addHandler(registryHandler(), "/registry");
		registry.addHandler(chatHandler(), "/chat");
		registry.addHandler(chat4Handler(), "/chat4");
		registry.addHandler(quoteHandler(), "/quotes");
	}

	@Bean
	public QuoteHandler quoteHandler() {
		return new QuoteHandler();
	}

	@Bean
	public QuoteService quoteService(QuoteHandler quoteHandler) {
		return new QuoteService(quoteHandler);
	}

	@Bean
	public WebSocketHandler simpleHandler() {
		return new SimpleHandler();
	}

	@Bean
	public RegistryHandler registryHandler() {
		return new RegistryHandler();
	}

	@Bean
	public RegistryHandler chatHandler() {
		return new ChatHandler(getAsyncExecutor());
	}

	@Bean
	public Chat4Handler chat4Handler() {
		return new Chat4Handler(getAsyncExecutor());
	}

	@Bean
	public MemoryObserver memoryObserver() {
		return new MemoryObserver(registryHandler());
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		executor.setThreadNamePrefix("MyExecutor-");
		executor.initialize();
		return executor;
	}

}
