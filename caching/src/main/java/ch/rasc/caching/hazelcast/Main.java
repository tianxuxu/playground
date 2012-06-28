package ch.rasc.caching.hazelcast;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hazelcast.core.HazelcastInstance;

public class Main {

	public static void main(final String[] args) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		Calculator calculator = ctx.getBean(Calculator.class);

		BigInteger result = calculator.factorial(69);
		System.out.println("69! = " + result);

		result = calculator.factorial(69);
		System.out.println("69! = " + result);

		TimeUnit.MINUTES.sleep(1);

		ctx.getBean(HazelcastInstance.class).getLifecycleService().shutdown();
	}

}
