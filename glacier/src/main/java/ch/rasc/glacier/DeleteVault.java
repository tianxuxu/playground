package ch.rasc.glacier;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteArchiveRequest;

public class DeleteVault {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			String archiveID = "f7rdsN2dqz7JcgeAeBzR4fefJDYcLKKqHxceMgiEvppaFC8dCWyJ8Q9Tlnotx-Al7wJxJpHxxHm-wCqZ2m5FY6qosGegHXiPzg633pYJtXzbQbpWsktlz0LpPXeyY0EChjI0B8guPQ";
			client.deleteArchive(new DeleteArchiveRequest().withVaultName("testvault").withArchiveId(archiveID));

			archiveID = "8FUoW7ipbxDVI_lcFzmyNKajdNTft2snPsHUfMPw6Ov-ZBVETV2-IHibzRp723h2cvFYhVnqvfXTIHlESdd9_6nQvsi_o0ArhDrBvo7xalwmjHJby_OTVESy8TcAcOe7kSWNJg7ipA";
			client.deleteArchive(new DeleteArchiveRequest().withVaultName("testvault").withArchiveId(archiveID));

			// client.deleteVault(new
			// DeleteVaultRequest().withVaultName("testvault"));

			// DB db = DBMaker.newFileDB(new
			// File("glacierdb")).closeOnJvmShutdown().asyncWriteDisable().make();
			// ConcurrentNavigableMap<Long, String> files =
			// db.getTreeMap("glacier");
			// for (String fileName : files.values()) {
			// String[] split = fileName.split(";");
			// String archivId = split[0];
			// System.out.println(archivId);
			// // DeleteArchiveRequest deleteArchiveRequest = new
			// //
			//
			// }
		}
	}

}
