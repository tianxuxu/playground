package ch.rasc.caching.hazelcast;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.caching.hazelcast" })
@EnableCaching
public class Config {

	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager(Hazelcast.getDefaultInstance());
	}

}
