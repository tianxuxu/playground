package ch.rasc.cors;

public class User {
	private final int id;

	private final String loginId;

	public User(final int id, final String loginId) {
		super();
		this.id = id;
		this.loginId = loginId;
	}

	public int getId() {
		return id;
	}

	public String getLoginId() {
		return loginId;
	}

}
