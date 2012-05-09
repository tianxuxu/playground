package ch.rasc.mongodb.author;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.rasc.mongodb.author.morphia.Word12;
import ch.rasc.mongodb.author.raw.RawAuthor;
import ch.rasc.mongodb.author.raw.RawTextImporter;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

@Configuration
public class AppConfiguration {

	//Documents: 258'552 43.9 MB

	@Bean(destroyMethod = "close")
	public Mongo mongo() throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("localhost");
		//mongo.setWriteConcern(WriteConcern.NONE); //184 sec
		//mongo.setWriteConcern(WriteConcern.NORMAL); //184 sec
		mongo.setWriteConcern(WriteConcern.SAFE); //279 sec
		//mongo.setWriteConcern(WriteConcern.FSYNC_SAFE);

		return mongo;
	}

	@Bean
	public DB db() throws UnknownHostException, MongoException {
		return mongo().getDB("mydb");
	}

	@Bean
	public DBCollection collection() throws UnknownHostException, MongoException {
		return db().getCollection("text");
	}

	@Bean
	public Datastore datastore() throws UnknownHostException, MongoException {
		Morphia morphia = new Morphia();
		morphia.map(Word12.class);

		Datastore ds = morphia.createDatastore(mongo(), "mydb");
		ds.ensureIndexes();
		return ds;
	}

	@Bean
	public Author author() {
		return new RawAuthor();
		//return new MorphiaAuthor();
	}

	@Bean
	public TextImporter textImporter() {
		return new RawTextImporter();
		//return new MorphiaTextImporter();
	}

}
