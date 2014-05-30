package ch.rasc.torrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

public class Main {

	public static void main(String[] args) throws UnknownHostException,
			IOException {

		String torrentUrl = args[0];
		File torrentFile;
		if (torrentUrl.startsWith("http")) {
			File localFile = new File("./t.torrent");
			URL website = new URL(args[0]);

			try (ReadableByteChannel rbc = Channels.newChannel(website
					.openStream());
					FileOutputStream fos = new FileOutputStream(localFile)) {
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
			torrentFile = localFile;
		}
		else {
			torrentFile = new File(torrentUrl);
		}

		System.out.println(torrentFile);
		File downloadDirectory = new File(".");
		downloadDirectory.mkdirs();

		Client client = new Client(InetAddress.getLocalHost(),
				SharedTorrent.fromFile(torrentFile, downloadDirectory));

		// You can optionally set download/upload rate limits
		// in kB/second. Setting a limit to 0.0 disables rate
		// limits.
		client.setMaxDownloadRate(50000.0);
		client.setMaxUploadRate(10.0);

		// At this point, can you either call download() to download the torrent
		// and
		// stop immediately after...
		client.download();

		// Or call client.share(...) with a seed time in seconds:
		// client.share(3600);
		// Which would seed the torrent for an hour after the download is
		// complete.

		// Downloading and seeding is done in background threads.
		// To wait for this process to finish, call:
		client.waitForCompletion();

		// At any time you can call client.stop() to interrupt the download.
	}

}
