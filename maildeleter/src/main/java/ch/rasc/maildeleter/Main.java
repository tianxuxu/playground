package ch.rasc.maildeleter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	// private final static Timer timer = new Timer();

	// called by procrun
	// public static void stop(@SuppressWarnings("unused") String... args) {
	// timer.cancel();
	// logger.info("service stopped");
	// }

	public static void main(String... args) {
		logger.info("service started");
		try {

			Path configFile = Paths.get("./config.yaml");

			if (!Files.exists(configFile)) {
				URL myLocationURL = Main.class.getProtectionDomain().getCodeSource().getLocation();
				Path myPath = Paths.get(myLocationURL.toURI());
				Path myDir = myPath.getParent();

				configFile = myDir.resolve("config.yaml");
			}

			if (Files.exists(configFile)) {
				Config config;
				try (InputStream is = Files.newInputStream(configFile)) {
					Yaml yaml = new Yaml();
					config = yaml.loadAs(is, Config.class);
				}

				// timer.scheduleAtFixedRate(new MailDeleter(config), 0,
				// TimeUnit.DAYS.toMillis(1));
				new MailDeleter(config).run();
			} else {
				// timer.cancel();
				logger.error("config file not found");
			}

		} catch (URISyntaxException | IOException e) {
			logger.error("error", e);
		}

	}

}
