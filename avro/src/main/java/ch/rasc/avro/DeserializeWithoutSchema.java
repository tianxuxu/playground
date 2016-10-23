package ch.rasc.avro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class DeserializeWithoutSchema {

	public static void main(String[] args) throws IOException {
		// Deserialize Users from disk
		File f = new File("users_without_schema.avro");

		try (FileInputStream fis = new FileInputStream(f)) {
			Decoder decoder = DecoderFactory.get().binaryDecoder(fis, null);

			DatumReader<User> reader = new SpecificDatumReader<>(User.class);

			User user = reader.read(null, decoder);
			System.out.println(user);
			user = reader.read(user, decoder);
			System.out.println(user);
			user = reader.read(user, decoder);
			System.out.println(user);
		}
	}

}
