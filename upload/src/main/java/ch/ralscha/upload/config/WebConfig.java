package ch.ralscha.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.ralscha.upload.web.FileManager;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ch.ralscha.upload.web" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public FileManager fileManager() {
		return new FileManager("c:/upload");
	}

	//	@Bean
	//	public MultipartResolver multipartResolver() {
	//		return new StandardServletMultipartResolver();
	//	}
}