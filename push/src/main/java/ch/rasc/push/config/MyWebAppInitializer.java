package ch.rasc.push.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(final ServletContext container) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebConfig.class);

		container.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(
				new GenericWebApplicationContext()));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/action/*");
	}
}