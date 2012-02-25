import java.io.IOException;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.LifecycleException;
import org.xml.sax.SAXException;

import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) throws ServletException, LifecycleException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {
		System.setProperty("spring.profiles.active", "development");
		EmbeddedTomcat et = new EmbeddedTomcat();
		et.start();
	}
}
