package ch.ralscha.sandbox;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ch.ralscha.springplayground.tx.Customer;
import ch.ralscha.springplayground.tx.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class SimpleTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void test() {
		List<Customer> allCustomers = customerRepository.findAll();
		assertEquals(1, allCustomers.size());

		for (Customer customer : allCustomers) {
			System.out.println(customer);
		}

	}

}
