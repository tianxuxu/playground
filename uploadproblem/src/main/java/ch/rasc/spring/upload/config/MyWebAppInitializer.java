package ch.rasc.spring.upload.config;

import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

	@SuppressWarnings("resource")
	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebConfig.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		// Register and map the dispatcher servlet

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(
				new GenericWebApplicationContext()));
		dispatcher.setLoadOnStartup(1);
		dispatcher.setMultipartConfig(new MultipartConfigElement((String) null));

		Set<String> mappingConflicts = dispatcher.addMapping("/");
		if (!mappingConflicts.isEmpty()) {
			System.out.println("MAPPING CONFLICT");
			System.out.println(mappingConflicts.iterator().next());
		}

		/*
		 * // Create the dispatcher servlet's Spring application context
		 * AnnotationConfigWebApplicationContext dispatcherContext = new
		 * AnnotationConfigWebApplicationContext();
		 * dispatcherContext.register(DispatcherConfig.class); // Register and
		 * map the dispatcher servlet ServletRegistration.Dynamic dispatcher =
		 * container.addServlet("dispatcher", new
		 * DispatcherServlet(dispatcherContext));
		 * dispatcher.setLoadOnStartup(1); dispatcher.addMapping("/");
		 */

	}
}