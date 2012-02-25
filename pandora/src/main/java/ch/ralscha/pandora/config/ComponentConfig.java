package ch.ralscha.pandora.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.ralscha.pandora" })
public class ComponentConfig {
	//nothing here
}