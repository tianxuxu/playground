package ch.rasc.caching;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Cacheable("maxSizeCache")
	public String getData(final String param) {
		System.out.println("inside getData() : " + param);
		return param.toUpperCase();
	}
}
