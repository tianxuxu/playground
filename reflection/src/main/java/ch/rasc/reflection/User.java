package ch.rasc.reflection;

public class User {
	private final int id = 1;

	public String email = "sr@ess.ch";

	public static String userName = "userName";

	private static String testName = "testName";

	@Model("test")
	private String name;

	public int getId() {
		System.out.println("CALLED getId()");
		return id;
	}

	// public User(int id, String name) {
	// this.id = id;
	// this.name = name;
	// }
	//
	// public int getId() {
	// return id;
	// }
	//
	// public String getName() {
	// return name;
	// }

}
