package ch.rasc.github;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.TrackingRefUpdate;

public class Mylyn {

	public static void main(final String[] args) throws Exception {

		Path backupDir = Paths.get("github_backup");
		Files.createDirectories(backupDir);

		RepositoryService service = new RepositoryService();
		for (org.eclipse.egit.github.core.Repository repo : service.getRepositories("ralscha")) {

			Path repoDir = backupDir.resolve(repo.getName());
			Files.createDirectories(repoDir);

			Path configFile = repoDir.resolve("config");
			if (Files.exists(configFile)) {
				System.out.println("fetching : " + repo.getName());
				Collection<TrackingRefUpdate> updates = Git.open(repoDir.toFile()).fetch().call()
						.getTrackingRefUpdates();

				for (TrackingRefUpdate update : updates) {
					System.out.println(update.getResult());
				}
			} else {
				System.out.println("cloning : " + repo.getName());
				Git.cloneRepository().setBare(true).setURI(repo.getGitUrl()).setDirectory(repoDir.toFile()).call();
			}

		}

		// Repository newRepo = Git.init().setBare(true).setDirectory(new
		// File("new")).call().getRepository();
		//
		// RemoteConfig config = new RemoteConfig(newRepo.getConfig(),
		// Constants.DEFAULT_REMOTE_NAME);
		// URIish u = new URIish("git://github.com/ralscha/embeddedtc.git");
		// config.addURI(u);
		// config.setMirror(true);
		//
		// RefSpec refSpec = new RefSpec();
		// refSpec = refSpec.setForceUpdate(true);
		// refSpec =
		// refSpec.setSourceDestination("refs/heads/*","refs/remotes/origin/*");
		// config.addFetchRefSpec(refSpec);
		//
		// config.update(newRepo.getConfig());
		// newRepo.getConfig().save();
		//
		//
		// Git.wrap(newRepo).fetch().call();
		//
		// newRepo.close();

	}

}
