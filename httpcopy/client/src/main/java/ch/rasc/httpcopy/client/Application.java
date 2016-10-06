package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {

		Path watchPath = Paths.get("e:/temp/in");
		Config config = new Config("http://localhost:8080/upload", watchPath);
		config.setOverwrite(false);

		WorkQueue wq = new WorkQueue();
		DirectoryWatchService dws = new DirectoryWatchService(wq);

		// list all files from the watched directory and add them to the working queue
		Files.list(watchPath).forEach(wq::add);

		dws.watch(watchPath);

		Uploader uploader = new Uploader(config, wq);
		while (true) {

			Set<WorkJob> paths = wq.getPaths();
			if (!paths.isEmpty()) {
				uploader.handlePaths(paths);
			}

			TimeUnit.SECONDS.sleep(5);
		}
	}

}
