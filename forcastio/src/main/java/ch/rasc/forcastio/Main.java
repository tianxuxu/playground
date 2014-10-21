package ch.rasc.forcastio;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import ch.rasc.forcastio.model.FioBlock;
import ch.rasc.forcastio.model.FioResponse;
import ch.rasc.forcastio.model.FioUnit;

public class Main {

	public static void main(String[] args) throws ClientProtocolException, IOException {

		// System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
		// System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
		// "true");
		// System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire",
		// "DEBUG");

		FioResponse response = FioRequest.create(args[0], "47.948915", "-100.1019949")
				.unit(FioUnit.SI).language(FioLanguage.DE).include(FioBlock.DAILY)
				// .exclude(FioBlock.ALERTS, FioBlock.FLAGS, FioBlock.CURRENTLY,
				// FioBlock.MINUTELY)
				.execute();
		System.out.println(response);

	}
}
