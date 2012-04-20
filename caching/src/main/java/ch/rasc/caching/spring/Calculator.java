package ch.rasc.caching.spring;

import java.math.BigInteger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Calculator {

	public BigInteger callFromInside() {
		System.out.println("calling factorial from inside Calculator");
		return factorial(69);
	}
	
	@Cacheable("calculator")
	public BigInteger factorial(long n) {
		System.out.println("calling factorial method with parameter: " + n);

		BigInteger ret = BigInteger.ONE;		
		for (int i = 1; i <= n; ++i) {
			ret = ret.multiply(BigInteger.valueOf(i));
		}

		return ret;

	}
	
	@CacheEvict(value="calculator", allEntries=true)
	public void clearCache() {
		//nothing here
	}
}
