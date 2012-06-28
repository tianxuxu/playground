package ch.rasc.javaplayground.hazelcast;

import java.util.Collection;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Instance;
import com.hazelcast.core.InstanceEvent;
import com.hazelcast.core.InstanceListener;

public class HCListener implements InstanceListener {

	public static void main(final String[] args) {
		HCListener main = new HCListener();
		Hazelcast.addInstanceListener(main);
		Collection<Instance> instances = Hazelcast.getInstances();
		for (Instance instance : instances) {
			System.out.println("ID: [" + instance.getId() + "] Type: [" + instance.getInstanceType() + "]");
		}

	}

	@Override
	public void instanceCreated(final InstanceEvent event) {
		Instance instance = event.getInstance();
		System.out
				.println("Created instance ID: [" + instance.getId() + "] Type: [" + instance.getInstanceType() + "]");

	}

	@Override
	public void instanceDestroyed(final InstanceEvent event) {
		Instance instance = event.getInstance();
		System.out.println("Destroyed isntance ID: [" + instance.getId() + "] Type: [" + instance.getInstanceType()
				+ "]");

	}
}