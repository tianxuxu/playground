package ch.ralscha.springplayground.tx;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class WorkingInitService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	//@Qualifier("readWriteTransactionTemplate")
	private TransactionTemplate readWriteTransactionTemplate;

	@PostConstruct
	public void init() {
		readWriteTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				doDbStuff();
			}
		});

		String result = readWriteTransactionTemplate.execute(new TransactionCallback<String>() {
			@Override
			public String doInTransaction(TransactionStatus status) {
				//do some db operation
				return "theResult";
			}
		});

		System.out.println(result);

	}

	void doDbStuff() {
		System.out.println("INSIDE working init()");
		Customer c = new Customer();
		c.setEmail("test@test.com");
		c.setFirstName("John");
		c.setName("Doe");

		Address a = new Address();
		a.setCity("city");
		a.setCountry("CH");
		a.setPostalCode("po");
		a.setStreet("aStreet");
		a.setType(Address.Type.HOME);

		a.setCustomer(c);
		c.getAddresses().add(a);

		entityManager.persist(c);
	}
}
