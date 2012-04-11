import org.springframework.web.SpringServletContainerInitializer;

import com.google.common.cache.CacheBuilder;

import ch.ralscha.embeddedtc.EmbeddedTomcat;
import ch.rasc.caching.config.MyWebAppInitializer;

public class StartTomcat {

	public static void main(String[] args) {
		EmbeddedTomcat.create()
			.addInitializer(SpringServletContainerInitializer.class, MyWebAppInitializer.class)
			.startAndWait();
	
	}
}
