package ch.rasc.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.rasc.upload.web.FileManager;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ch.rasc.upload.web" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("#{environment.uploadDirectory ?: 'c:/temp'}")
	private String uploadDirectory;

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public FileManager fileManager() {
		return new FileManager(uploadDirectory);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}