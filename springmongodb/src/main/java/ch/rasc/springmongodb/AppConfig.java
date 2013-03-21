package ch.rasc.springmongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class AppConfig extends AbstractMongoConfiguration {

	@Override
	public Mongo mongo() throws Exception {

		// MongoOptions options = new MongoOptions();
		// options.connectionsPerHost = 8;
		// options.threadsAllowedToBlockForConnectionMultiplier = 4;
		// options.connectTimeout = 1000;
		// options.maxWaitTime = 1500;
		// options.autoConnectRetry = true;
		// options.socketKeepAlive = true;
		// options.socketTimeout = 1500;
		// options.slaveOk = true;
		// options.w = 1;
		// options.wtimeout = 0;
		// options.fsync = false;

		return new MongoClient("localhost"/* , options */);
	}

	@Override
	public String getDatabaseName() {
		return "linkedin";
	}

	@Override
	public String getMappingBasePackage() {
		return "ch.rasc.springmongodb.domain";
	}

}