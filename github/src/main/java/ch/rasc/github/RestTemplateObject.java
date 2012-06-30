package ch.rasc.github;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateObject {

	private static final String GITHUB_REPOS_URI = "https://api.github.com/users/{user}/repos";

	public static void main(final String[] args) {

		RestTemplate restTemplate = new RestTemplate();

		Repo[] repos = restTemplate.getForObject(GITHUB_REPOS_URI, Repo[].class, "ralscha");
		for (Repo repo : repos) {
			System.out.println("Name: " + repo.name);
			System.out.println("URL: " + repo.git_url);
			System.out.println();

		}
	}

}
