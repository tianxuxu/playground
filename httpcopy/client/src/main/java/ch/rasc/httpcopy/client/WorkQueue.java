package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WorkQueue {
	private final Set<WorkJob> inbox = new HashSet<>();

	public synchronized void add(Path p) {
		this.inbox.add(new WorkJob(p));
	}

	public void add(Set<WorkJob> paths) {
		this.inbox.addAll(paths);
	}

	public void add(WorkJob job) {
		this.inbox.add(job);
	}

	// return all paths that have a modified date older than 10 seconds
	public synchronized Set<WorkJob> getPaths() throws IOException {
		Set<WorkJob> result = new HashSet<>();
		long now = System.currentTimeMillis() / 1000;

		for (WorkJob job : this.inbox) {
			Path path = job.getFile();
			FileTime time = Files.getLastModifiedTime(path);
			if (time.to(TimeUnit.SECONDS) + 10 <= now) {
				result.add(job);
			}
		}

		this.inbox.removeAll(result);
		return result;
	}

}
