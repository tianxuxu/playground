package ch.rasc.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {
	public String name;

	public String url;

	@Override
	public String toString() {
		return "Repo [name=" + name + ", url=" + url + "]";
	}

}
