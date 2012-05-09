package ch.rasc.springplayground.tx;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Address extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -2961520776208650065L;

	public enum Type {
		WORK, HOME
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;

	@Length(max = 255)
	private String street;

	@Length(max = 50)
	private String postalCode;

	@Length(max = 255)
	private String city;

	@Length(max = 2)
	private String country;

	@ManyToOne
	private Customer customer;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Address [type=" + type + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city
				+ ", country=" + country + ", customer=" + customer + "]";
	}

}
