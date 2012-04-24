package ch.rasc.caching.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Test {

	public static void main(String[] args) {
		Cache<Integer,String> cache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(10, TimeUnit.SECONDS).build();
		cache.put(1, "one");		
		System.out.println(cache.getIfPresent(1));
	}

}
