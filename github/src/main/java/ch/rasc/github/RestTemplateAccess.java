package ch.rasc.github;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateAccess {

	private static final String GITHUB_REPOS_URI = "https://api.github.com/users/{user}/repos";

	public static void main(final String[] args) {

		RestTemplate restTemplate = new RestTemplate();

		// List<Map<String, Object>> repos =
		// restTemplate.getForObject(GITHUB_REPOS_URI, List.class, "ralscha");
		//
		// for (Map<String, Object> repo : repos) {
		// System.out.println("Name: " + repo.get("name"));
		// System.out.println("URL: " + repo.get("url"));
		// System.out.println();
		// }

		Repo[] repos = restTemplate.getForObject(GITHUB_REPOS_URI, Repo[].class, "ralscha");
		for (Repo repo : repos) {
			System.out.println(repo);
		}
	}

}
