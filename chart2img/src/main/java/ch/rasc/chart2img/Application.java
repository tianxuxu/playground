package ch.rasc.chart2img;

import java.util.Collections;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class).run(args);
	}

	@Bean
	public ServletRegistrationBean svg2pngServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setUrlMappings(Collections.singleton("/chart2img"));
		reg.setServlet(new Chart2ImgServlet());
		return reg;
	}
}
