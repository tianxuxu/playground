package ch.rasc.javaplayground.hazelcast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.hazelcast.core.Hazelcast;

public class QueueOffer {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> q = Hazelcast.getQueue("numbers");

		for (int i = 0; i < 10000; i++) {
			q.put(i);
			TimeUnit.SECONDS.sleep(5);
		}

	}

}
