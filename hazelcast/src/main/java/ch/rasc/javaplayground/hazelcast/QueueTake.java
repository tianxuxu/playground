package ch.rasc.javaplayground.hazelcast;

import java.util.concurrent.BlockingQueue;

import com.hazelcast.core.Hazelcast;

public class QueueTake {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> q = Hazelcast.getQueue("numbers");
		Integer t;
		while ((t = q.take()) != null) {
			System.out.println(t);
		}
	}

}
