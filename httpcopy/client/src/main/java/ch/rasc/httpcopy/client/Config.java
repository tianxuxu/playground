package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class Config {

	private final String clientId;

	private final String serverEndpoint;

	private final Path watchDir;

	// true: overwrite files on the server when they have the same filename (default)
	// false: rename file when a file with the same name exists (e.g. test.txt ->
	// test_1.txt)
	private boolean overwrite;

	public Config(String serverEndpoint, Path watchDir) throws IOException {
		this.overwrite = true;
		this.serverEndpoint = serverEndpoint;
		this.watchDir = watchDir;

		Path clientIdPath = Paths.get(System.getProperty("user.dir"), "client-id");
		if (Files.exists(clientIdPath)) {
			this.clientId = new String(Files.readAllBytes(clientIdPath),
					StandardCharsets.ISO_8859_1);
		}
		else {
			this.clientId = getRandomId();
			Files.write(clientIdPath,
					this.clientId.getBytes(StandardCharsets.ISO_8859_1));
		}
	}

	public static String getRandomId() {
		UUID uuid = UUID.randomUUID();
		byte[] idBytes = ByteBuffer.allocate(16).putLong(uuid.getLeastSignificantBits())
				.putLong(uuid.getMostSignificantBits()).array();
		return Base64.getUrlEncoder().encodeToString(idBytes);
	}

	public boolean isOverwrite() {
		return this.overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public String getClientId() {
		return this.clientId;
	}

	public String getServerEndpoint() {
		return this.serverEndpoint;
	}

	public Path getWatchDir() {
		return this.watchDir;
	}

}
