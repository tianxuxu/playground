package ch.rasc.mongodb.morphia;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class Insert {

	public static void main(String[] args) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("ch.rasc.mongodb.morphia");

		try (MongoClient mongoClient = new MongoClient()) {
			mongoClient.getDatabase("morphia_example").getCollection("employees").drop();

			Datastore datastore = morphia.createDatastore(mongoClient, "morphia_example");
			datastore.ensureIndexes();

			Employee elmer = new Employee("Elmer Fudd", 50000.0);

			Employee daffy = new Employee("Daffy Duck", 40000.0);
			daffy.setFirstname("ralph");
			datastore.save(daffy);

			Employee pepe = new Employee("Pepé Le Pew", 25000.0);
			datastore.save(pepe);

			elmer.getDirectReports().add(daffy);
			elmer.getDirectReports().add(pepe);

			datastore.save(elmer);
		}

	}

}
