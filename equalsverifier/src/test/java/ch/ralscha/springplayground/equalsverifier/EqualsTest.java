package ch.ralscha.springplayground.equalsverifier;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

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
