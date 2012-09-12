package ch.rasc.reflection;

@Model(value = "User", paging = true, readMethod = "read", createMethod = "create", updateMethod = "update", destroyMethod = "destroy")
public class User extends Base {

	private String name;

	public String firstName;

	private String veryPrivate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
