package ch.rasc.sec.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ch.rasc.sec.controller.HelloWorldController;
import ch.rasc.sec.security.SecurityConfig;

@Configuration
@ComponentScan(basePackageClasses = { HelloWorldController.class, SecurityConfig.class })
public class ComponentConfig {
	// nothing here
}