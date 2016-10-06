package ch.rasc.httpcopy.client;

import java.nio.file.Path;

public class WorkJob {
	private final String id;

	private final Path file;

	private String alternativeFilename;

	public WorkJob(Path file) {
		this.id = Config.getRandomId();
		this.file = file;
	}

	public String getFilenameOrAlternative() {
		if (this.alternativeFilename != null) {
			return this.alternativeFilename;
		}
		return this.file.getFileName().toString();
	}

	public String getAlternativeFilename() {
		return this.alternativeFilename;
	}

	public void setAlternativeFilename(String alternativeFilename) {
		this.alternativeFilename = alternativeFilename;
	}

	public Path getFile() {
		return this.file;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.file == null ? 0 : this.file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorkJob other = (WorkJob) obj;
		if (this.file == null) {
			if (other.file != null) {
				return false;
			}
		}
		else if (!this.file.equals(other.file)) {
			return false;
		}
		return true;
	}

}
