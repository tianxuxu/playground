package ch.rasc.githubbackup;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
		
		disableCertificateValidation();

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

	private static void fetchRepo(Path backupDir, String name, String url, String username, String password)
			throws IOException, GitAPIException, InvalidRemoteException, TransportException {
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
			if (username != null) {
				UsernamePasswordCredentialsProvider cp = new UsernamePasswordCredentialsProvider(username, password);
				Git.cloneRepository().setCredentialsProvider(cp).setBare(true).setURI(url).setDirectory(repoDir.toFile()).call();
			} else {
				Git.cloneRepository().setBare(true).setURI(url).setDirectory(repoDir.toFile()).call();
			}
			
		}
	}

	public static void disableCertificateValidation() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				//nothing here
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				//nothing here
			}
		} };

		// Ignore differences between given hostname and certificate hostname
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (Exception e) {
			//nothing here
		}
	}
}
