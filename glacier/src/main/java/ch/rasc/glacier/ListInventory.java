package ch.rasc.glacier;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class ListInventory {


	public static void main(String[] args) {
		DB db = DBMaker.newFileDB(new File("glacierdb")).closeOnJvmShutdown().asyncWriteDisable().make();
		ConcurrentNavigableMap<Long, String> files = db.getTreeMap("glacier");

		for (Long ts : files.keySet()) {
			System.out.println(ts + " --> " + files.get(ts));
		}
		
		db.close();
	}

}
