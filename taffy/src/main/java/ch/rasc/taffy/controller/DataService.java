package ch.rasc.taffy.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class DataService {

	private List<User> users;

	@PostConstruct
	public void readData() throws IOException {

		Path tempFile = Files.createTempFile("random", "zip");
		Files.copy(DataService.class.getResourceAsStream("/randomdata.zip"),
				tempFile, StandardCopyOption.REPLACE_EXISTING);

		List<User> listBuilder = new ArrayList<>();

		try (FileSystem zipFs = FileSystems.newFileSystem(tempFile, null)) {
			Path dataPath = zipFs.getPath("/randomdata.csv");
			try (CSVReader reader = new CSVReader(Files.newBufferedReader(
					dataPath, StandardCharsets.ISO_8859_1), ';')) {
				reader.readNext();
				String[] line;
				while ((line = reader.readNext()) != null) {
					listBuilder.add(new User(line));
				}
			}
		}
		Files.delete(tempFile);

		users = Collections.unmodifiableList(listBuilder);
	}

	public List<User> getUsers() {
		return users;
	}

}
