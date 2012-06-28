package ch.rasc.mongodb.geolite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;

public class FindIpFast {

	public static void main(final String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext("ch.rasc.mongodb.geolite");
		Datastore datastore = ctx.getBean(Datastore.class);

		// long myIp = 16777216l * 188 + 65536l * 61 + 256l * 140l + 26;
		long firstIp = 16777217; // first document
		long lastIp = 3758095359l; // last document
		long testIp = 1275328930;

		// first document
		System.out.print("First document: ");
		// Warmup
		findFast(datastore, firstIp);
		findFast(datastore, firstIp);

		// last document
		System.out.print("Last document: ");
		findFast(datastore, lastIp);

		// test document
		System.out.print("Test document: ");
		findFast(datastore, testIp);

	}

	private static void findFast(final Datastore datastore, final long ip) {
		Query<Geolite> q = datastore.createQuery(Geolite.class);
		q.field("startIpNum").lessThanOrEq(ip);
		q.order("-startIpNum");
		q.limit(1);
		long start = System.currentTimeMillis();
		Geolite geo = q.get();
		System.out.println((System.currentTimeMillis() - start) + " ms");
		System.out.println(geo);
	}

}
