package ch.rasc.taffy.controller;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;

@Service
public class DataService {

	private ImmutableList<User> users;

	@PostConstruct
	public void readData() throws IOException {

		Path tempFile = Files.createTempFile("random", "zip");
		Files.copy(DataService.class.getResourceAsStream("/randomdata.zip"), tempFile,
				StandardCopyOption.REPLACE_EXISTING);
		
		ImmutableList.Builder<User> listBuilder = ImmutableList.builder();

		try (FileSystem zipFs = FileSystems.newFileSystem(tempFile, null)) {
			Path dataPath = zipFs.getPath("/randomdata.csv");
			CSVReader reader = new CSVReader(Files.newBufferedReader(dataPath, Charsets.ISO_8859_1), ';');
			reader.readNext();
			String[] line;
			while ((line = reader.readNext()) != null) {
				listBuilder.add(new User(line));
			}
			reader.close();			
		}
		Files.delete(tempFile);

		users = listBuilder.build();
	}

	public ImmutableList<User> getUsers() {
		return users;
	}

}
