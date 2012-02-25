package ch.ralscha.springplayground.tx;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	//nothing here
}
