package ch.rasc.glacier;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;

public class DeleteVault {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			// DeleteVaultRequest request = new
			// DeleteVaultRequest().withVaultName("testvault");
			// client.deleteVault(request);

			DB db = DBMaker.newFileDB(new File("glacierdb")).closeOnJvmShutdown().asyncWriteDisable().make();
			ConcurrentNavigableMap<Long, String> files = db.getTreeMap("glacier");
			for (String fileName : files.values()) {
				String[] split = fileName.split(";");
				String archivId = split[0];
				System.out.println(archivId);
				// DeleteArchiveRequest deleteArchiveRequest = new
				// DeleteArchiveRequest().withVaultName("testvault").withArchiveId(archivId);
				// client.deleteArchive(deleteArchiveRequest);

			}
		}
	}

}
