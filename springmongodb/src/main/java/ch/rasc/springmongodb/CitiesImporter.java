package ch.rasc.springmongodb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import ch.rasc.springmongodb.domain.City;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class CitiesImporter {

	public static void main(String[] args) throws IOException, URISyntaxException {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
			ctx.register(AppConfig.class);
			ctx.refresh();

			final MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);

			if (mongoOps.collectionExists(City.class)) {
				System.out.println("DROP");
				mongoOps.dropCollection(City.class);
			} else {
				System.out.println("NO DROP");
			}

			File f = new File(CitiesImporter.class.getResource("/worldcitiespop.txt").toURI());

			Files.readLines(f, Charsets.ISO_8859_1, new LineProcessor<String>() {
				@Override
				public boolean processLine(String line) throws IOException {
					if (!line.startsWith("Country,City")) {
						String[] splittedItems = StringUtils.commaDelimitedListToStringArray(line);
						if (splittedItems.length == 7) {

							City newCity = new City();
							newCity.setCountry(splittedItems[0]);
							newCity.setAsciiCityName(splittedItems[1]);
							newCity.setCityName(splittedItems[2]);
							newCity.setRegion(splittedItems[3]);

							String populationString = splittedItems[4];
							if (StringUtils.hasText(populationString)) {
								newCity.setPopulation(Integer.valueOf(populationString));
							}

							String latitudeStr = splittedItems[5];
							String longitudeStr = splittedItems[6];

							if (latitudeStr.equals("180.0")) {
								latitudeStr = "179.9999";
							}

							if (longitudeStr.equals("180.0")) {
								longitudeStr = "179.9999";
							}

							newCity.setLocation(new Point(Double.valueOf(latitudeStr), Double.valueOf(longitudeStr)));

							mongoOps.save(newCity);
						}
					}

					return true;
				}

				@Override
				public String getResult() {
					return null;
				}
			});
		}

	}

}
