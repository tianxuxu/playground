package ch.rasc.jpa.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class Author {

	@NotEmpty
	private String lastName;

	@NotEmpty(groups = Draft.class)
	private String firstName;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
