package ch.rasc.equalsverifier;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

import ch.rasc.springplayground.equalsverifier.Customer;
import ch.rasc.springplayground.equalsverifier.Point;

public class EqualsTest {

	@Test
	public void testCustomerEquals() {
		EqualsVerifier.forClass(Customer.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
	}

	@Test
	public void testPointEquals() {
		EqualsVerifier.forClass(Point.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
