package ch.rasc.caching.guava;

import java.math.BigInteger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Calculator {

	@Cacheable("oneMinuteCache")
	public BigInteger factorialTimedCache(final long n) {
		System.out.println("  inside factorial method parameter: " + n);
		return doCalculation(n);
	}

	@Cacheable("maxSizeCache")
	public BigInteger factorialMaxCache(final long n) {
		System.out.println("  inside factorial method parameter: " + n);
		return doCalculation(n);
	}

	@Cacheable("maxSizeCache")
	public String doSomething(final String u) {
		System.out.println("inside doSomething");
		return u;
	}

	private BigInteger doCalculation(final long n) {
		BigInteger ret = BigInteger.ONE;
		for (int i = 1; i <= n; ++i) {
			ret = ret.multiply(BigInteger.valueOf(i));
		}

		return ret;
	}

}
