package ch.rasc.caching.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.caching.spring" })
@EnableCaching(mode = AdviceMode.ASPECTJ)
// @EnableCaching
public class Config implements CachingConfigurer {

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("calculator", "anotherCache");
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}

}
