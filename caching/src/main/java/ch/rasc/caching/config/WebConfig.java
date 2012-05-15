package ch.rasc.caching.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.rasc.caching.guava.GuavaCache;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackages = { "ch.rasc.caching" })
public class WebConfig extends WebMvcConfigurerAdapter implements CachingConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		// cacheManager.setCaches(Arrays.asList(new
		// ConcurrentMapCache("default")));

		Cache<Object, Optional<Object>> tenMinutesCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

		Cache<Object, Optional<Object>> maxSizeCache = CacheBuilder.newBuilder().maximumSize(10).build();

		cacheManager.setCaches(Arrays.asList(new GuavaCache("tenMinutesCache", tenMinutesCache), new GuavaCache("maxSizeCache",
				maxSizeCache)));

		return cacheManager;
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}

}