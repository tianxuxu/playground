package ch.rasc.sec.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ch.rasc.sec.controller.HelloWorldController;

@Configuration
@ComponentScan(basePackageClasses = HelloWorldController.class)
public class ComponentConfig {
	// nothing here
}