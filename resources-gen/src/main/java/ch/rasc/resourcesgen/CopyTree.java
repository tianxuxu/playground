package ch.rasc.resourcesgen;

import java.io.IOException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyTree implements FileVisitor<Path> {

	private final Path from;

	private final Path to;

	public CopyTree(Path from, Path to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		Path newdir = to.resolve(from.relativize(dir));
		try {
			Files.copy(dir, newdir, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException e) {
			System.err.println("Unable to create " + newdir + " [" + e + "]");
			return FileVisitResult.SKIP_SUBTREE;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		try {
			Files.copy(file, to.resolve(from.relativize(file)), StandardCopyOption.REPLACE_EXISTING,
					StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException e) {
			System.err.println("Unable to copy " + file + " [" + e + "]");
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		if (exc instanceof FileSystemLoopException) {
			System.err.println("Cycle was detected: " + file);
		} else {
			System.err.println("Error occurred, unable to copy:" + file + " [" + exc + "]");
		}
		return FileVisitResult.CONTINUE;
	}

}
