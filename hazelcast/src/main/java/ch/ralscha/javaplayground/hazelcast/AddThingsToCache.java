package ch.ralscha.javaplayground.hazelcast;

import java.util.List;
import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class AddThingsToCache {

	private HazelcastInstance haz;

	public AddThingsToCache() {
		haz = Hazelcast.getDefaultInstance();
	}

	public void addListData() {
		List<ObjectToCache> list = haz.getList("testlist");
		for (int i = 0; i < 10000; i++) {
			list.add(new ObjectToCache("example: " + i, "value" + i, i));
		}
	}

	public void addMapData() {
		Map<Integer, ObjectToCache> map = haz.getMap("testmap");
		for (int i = 0; i < 10000; i++) {
			map.put(i, new ObjectToCache("example: " + i, "value" + i, i));
		}
	}

	public static void main(String[] args) {
		AddThingsToCache adder = new AddThingsToCache();
		adder.addListData();
		adder.addMapData();
	}

}