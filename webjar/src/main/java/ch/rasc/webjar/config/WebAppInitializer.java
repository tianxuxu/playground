package ch.rasc.webjar.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		try (AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
				GenericWebApplicationContext emptyContext = new GenericWebApplicationContext()) {
			rootContext.register(SpringConfig.class);
			container.addListener(new ContextLoaderListener(rootContext));

			ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(
					emptyContext));
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/");
		}
	}
}