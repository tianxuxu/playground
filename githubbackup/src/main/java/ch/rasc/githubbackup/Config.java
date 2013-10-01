package ch.rasc.githubbackup;

import java.util.List;

public class Config {
	private String backupDirectory;

	private List<String> githubUsers;

	private List<GitUrl> gitUrls;

	private String key;

	private String knownHosts;

	public String getBackupDirectory() {
		return backupDirectory;
	}

	public void setBackupDirectory(String backupDirectory) {
		this.backupDirectory = backupDirectory;
	}

	public List<String> getGithubUsers() {
		return githubUsers;
	}

	public void setGithubUsers(List<String> githubUsers) {
		this.githubUsers = githubUsers;
	}

	public List<GitUrl> getGitUrls() {
		return gitUrls;
	}

	public void setGitUrls(List<GitUrl> gitUrls) {
		this.gitUrls = gitUrls;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKnownHosts() {
		return knownHosts;
	}

	public void setKnownHosts(String knownHosts) {
		this.knownHosts = knownHosts;
	}

	public final static class GitUrl {
		public String name;

		public String url;

		public String username;

		public String password;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}
}
