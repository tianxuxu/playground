package ch.rasc.mongodb.geolite;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

@Configuration
public class AppConfiguration {

	// Documents: 258'552 43.9 MB

	@Bean(destroyMethod = "close")
	public MongoClient mongoClient() throws UnknownHostException, MongoException {
		MongoClient mongo = new MongoClient("localhost");
		// mongo.setWriteConcern(WriteConcern.NONE); //184 sec
		mongo.setWriteConcern(WriteConcern.NORMAL); // 184 sec
		// mongo.setWriteConcern(WriteConcern.SAFE); //279 sec
		// mongo.setWriteConcern(WriteConcern.FSYNC_SAFE);

		return mongo;
	}

	@Bean
	public Datastore datastore() throws UnknownHostException, MongoException {
		Morphia morphia = new Morphia();
		morphia.map(Geolite.class);

		Datastore ds = morphia.createDatastore(mongoClient(), "mydb");
		ds.ensureIndexes();
		return ds;
	}

}
