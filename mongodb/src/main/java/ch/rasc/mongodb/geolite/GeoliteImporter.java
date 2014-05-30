package ch.rasc.mongodb.geolite;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import au.com.bytecode.opencsv.CSVReader;

import com.google.code.morphia.Datastore;

@Named
public class GeoliteImporter {

	@Inject
	private Datastore datastore;

	public void importData() throws IOException {
		// D:\_download\GeoLiteCity_20101101\GeoLiteCity-Blocks.csv
		// D:\_download\GeoLiteCity_20101101\GeoLiteCity-Location.csv

		Map<Integer, Geolite> locationMap = new HashMap<>();

		try (CSVReader reader = new CSVReader(
				new FileReader(
						"D:\\_download\\GeoLiteCity_20101101\\GeoLiteCity-Location.csv"))) {
			reader.readNext();
			reader.readNext();
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// locId,country,region,city,postalCode,latitude,longitude,metroCode,areaCode
				int locId = Integer.valueOf(nextLine[0]);
				Geolite geolite = new Geolite();
				geolite.setCountry(nextLine[1]);
				geolite.setRegion(nextLine[2]);
				geolite.setCity(nextLine[3]);
				geolite.setPostalCode(nextLine[4]);
				geolite.setLatitude(Double.valueOf(nextLine[5]));
				geolite.setLongitude(Double.valueOf(nextLine[6]));
				geolite.setMetroCode(nextLine[7]);
				geolite.setAreaCode(nextLine[8]);

				locationMap.put(locId, geolite);
			}
		}

		try (CSVReader reader = new CSVReader(new FileReader(
				"D:\\_download\\GeoLiteCity_20101101\\GeoLiteCity-Blocks.csv"))) {
			reader.readNext();
			reader.readNext();
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				long startIp = Long.valueOf(nextLine[0]);
				long endIp = Long.valueOf(nextLine[1]);
				int locId = Integer.valueOf(nextLine[2]);

				Geolite geolite = locationMap.get(locId);
				geolite.setStartIpNum(startIp);
				geolite.setEndIpNum(endIp);
				geolite.setId(null);
				datastore.save(geolite);
			}
		}

	}
}
