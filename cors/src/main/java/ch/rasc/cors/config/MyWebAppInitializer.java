package ch.rasc.cors.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		try (AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
				GenericWebApplicationContext webApplicationContext = new GenericWebApplicationContext()) {
			rootContext.register(WebConfig.class);

			container.addListener(new ContextLoaderListener(rootContext));

			final DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);

			dispatcherServlet.setDispatchOptionsRequest(true);
			/*
			 * <init-param> <param-name>dispatchOptionsRequest</param-name>
			 * <param-value>true</param-value> </init-param>
			 */

			ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);
			dispatcher.setLoadOnStartup(1);

			dispatcher.addMapping("/action/*");
		}
	}
}