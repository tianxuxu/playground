package ch.rasc.springplayground.tx;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Customer extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1239454015970198197L;

	@Length(max = 200)
	private String name;

	@Length(max = 200)
	private String firstName;

	@Email
	@Length(max = 255)
	@NotNull
	private String email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private Set<Address> addresses = new HashSet<Address>();

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(final Set<Address> addresses) {
		this.addresses = addresses;
	}

}
