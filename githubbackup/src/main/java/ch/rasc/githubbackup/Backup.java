package ch.rasc.githubbackup;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.yaml.snakeyaml.Yaml;

import ch.rasc.githubbackup.Config.GitUrl;

public class Backup {

	public static void main(String... args) throws Exception {

		if (args.length != 1) {
			System.out.println("java -jar githubbackup.jar <settings.yaml>");
			return;
		}

		Path settingsYamlPath = Paths.get(args[0]);
		try (BufferedReader br = Files.newBufferedReader(settingsYamlPath, StandardCharsets.UTF_8)) {
			Config config = new Yaml().loadAs(br, Config.class);

			if (config.getKey() != null) {
				CustomJschConfigSessionFactory jschConfigSessionFactory = new CustomJschConfigSessionFactory(
						config.getKey(), config.getKnownHosts());
				SshSessionFactory.setInstance(jschConfigSessionFactory);
			}

			Path backupDir = Paths.get(config.getBackupDirectory());
			Files.createDirectories(backupDir);

			RepositoryService service = new RepositoryService();
			if (config.getGithubUsers() != null) {
				for (String githubUser : config.getGithubUsers()) {
					for (Repository repo : service.getRepositories(githubUser)) {
						fetchRepo(backupDir, repo.getName(), repo.getGitUrl(), null, null);
					}
				}
			}

			if (config.getGitUrls() != null) {
				for (GitUrl gitUrl : config.getGitUrls()) {
					fetchRepo(backupDir, gitUrl.getName(), gitUrl.getUrl(), gitUrl.getUsername(), gitUrl.getPassword());
				}
			}
		}

	}

	private static void fetchRepo(Path backupDir, String name, String url, String username, String password) throws IOException, GitAPIException,
			InvalidRemoteException, TransportException {
		Path repoDir = backupDir.resolve(name);
		Files.createDirectories(repoDir);

		Path configFile = repoDir.resolve("config");
		if (Files.exists(configFile)) {
			System.out.println("fetching : " + name);
			if (username != null) {
				UsernamePasswordCredentialsProvider cp = new UsernamePasswordCredentialsProvider(username, password);
				Git.open(repoDir.toFile()).fetch().setCredentialsProvider(cp).call();
			} else {
				Git.open(repoDir.toFile()).fetch().call();
			}
		} else {
			System.out.println("cloning : " + name);
			Git.cloneRepository().setBare(true).setURI(url).setDirectory(repoDir.toFile()).call();
		}
	}

}
