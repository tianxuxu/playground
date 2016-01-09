package ch.rasc.longpolling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LongpollingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LongpollingApplication.class, args);
	}
}
