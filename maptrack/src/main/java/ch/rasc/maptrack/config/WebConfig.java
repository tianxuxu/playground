package ch.rasc.maptrack.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ch.rasc.maptrack" })
public class WebConfig extends WebMvcConfigurerAdapter {
	//nothing here
}