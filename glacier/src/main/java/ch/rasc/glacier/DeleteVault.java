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

			// client.deleteVault(new
			// DeleteVaultRequest().withVaultName("testvault"));

			String archiveID = "K94B9EdbegUnVNZGlItodYhOv5UP9H9JFzQmTFKXulv2UDuS5swbTtyA9W3_UEiMAfXXShCDz4WngHEbrJ71htmmQjcTg1BlJL7kDoI2ahkDxVBO_NLWGlR2dwSoS2BL3aE4KXIYrQ";
			client.deleteArchive(new DeleteArchiveRequest().withVaultName("testvault").withArchiveId(archiveID));

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
			// DeleteArchiveRequest().withVaultName("testvault").withArchiveId(archivId);
			// // client.deleteArchive(deleteArchiveRequest);
			//
			// }
		}
	}

}
