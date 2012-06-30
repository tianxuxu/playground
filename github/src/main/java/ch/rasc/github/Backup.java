package ch.rasc.github;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;

public class Backup {

	public static void main(final String[] args) throws Exception {

		Path backupDir = Paths.get("github_backup");
		Files.createDirectories(backupDir);

		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories("ralscha")) {

			Path repoDir = backupDir.resolve(repo.getName());
			Files.createDirectories(repoDir);

			Path configFile = repoDir.resolve("config");
			if (Files.exists(configFile)) {
				System.out.println("fetching : " + repo.getName());
				Git.open(repoDir.toFile()).fetch().call();
			} else {
				System.out.println("cloning : " + repo.getName());
				Git.cloneRepository().setBare(true).setURI(repo.getGitUrl()).setDirectory(repoDir.toFile()).call();
			}

		}

	}

}
