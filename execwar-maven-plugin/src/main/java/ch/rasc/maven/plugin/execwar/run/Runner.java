package ch.rasc.maven.plugin.execwar.run;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Runner {

	public static void main(String[] args) throws IOException {

		Path extractDir = Paths.get("tc");
		boolean extractWar = true;

		if (Files.exists(extractDir)) {
			Path timestampFile = extractDir.resolve("EXECWAR_TIMESTAMP");
			if (Files.exists(timestampFile)) {
				byte[] extractTimestampBytes = Files.readAllBytes(timestampFile);
				String extractTimestamp = new String(extractTimestampBytes, StandardCharsets.UTF_8);

				String timestamp = null;
				try (InputStream is = Runner.class.getResourceAsStream("/EXECWAR_TIMESTAMP");
						ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
					
					copy(is, bos);
					timestamp = new String(bos.toByteArray(), StandardCharsets.UTF_8);
				}

				if (Long.valueOf(timestamp) <= Long.valueOf(extractTimestamp)) {
					extractWar = false;
				}

			}
		}

		if (extractWar) {
			System.out.println("EXTRACT WAR");

			try {
				if (Files.exists(extractDir)) {
					Files.walkFileTree(extractDir, new DeleteDirectory());
				}

				Files.createDirectories(extractDir);
				Files.createDirectory(extractDir.resolve("temp"));
				Files.createDirectory(extractDir.resolve("work"));
				Files.createDirectory(extractDir.resolve("logs"));

				CodeSource src = Runner.class.getProtectionDomain().getCodeSource();
				List<String> warList = new ArrayList<>();

				if (src != null) {
					URL jar = src.getLocation();
					ZipInputStream zip = new ZipInputStream(jar.openStream());
					ZipEntry ze = null;

					while ((ze = zip.getNextEntry()) != null) {
						String entryName = ze.getName();
						if (entryName.endsWith(".war")) {
							warList.add(entryName);
						}
					}
				}
				
				for (String war : warList) {					
					Path warFile = extractDir.resolve(war);
					try (InputStream is = Runner.class.getResourceAsStream("/" + war)) {
						Files.copy(is, warFile);
					}
				}		
				
				Path timestampFile = extractDir.resolve("EXECWAR_TIMESTAMP");
				try (InputStream is = Runner.class.getResourceAsStream("/EXECWAR_TIMESTAMP")) {
					Files.copy(is, timestampFile);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("NO EXTRACT NEEDED");
		}

	}

	private static void copy(InputStream source, OutputStream sink) throws IOException {
		byte[] buf = new byte[8192];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
		}
	}

}
