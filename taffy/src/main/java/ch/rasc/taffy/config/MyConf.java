package ch.rasc.taffy.config;

import org.springframework.stereotype.Component;

@Component
public class MyConf {
	private String env;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
	
}
