import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadCsv1c {

	public ReadCsv1c() throws IOException {
		InputStream is = getClass().getResourceAsStream("/test1.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line;
		while ((line = br.readLine()) != null) {
			String[] splittedLine = line.split(",");

			String name = splittedLine[0];
			String email = splittedLine[1];
			Integer id = Integer.valueOf(splittedLine[2]);

			System.out.printf("%3d %-15s %s\n", id, name, email);
		}

		br.close();
		is.close();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		new ReadCsv1c();
	}

}
