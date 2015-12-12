package ch.rasc.reactorsandbox.quickstart.spring.repository;

import org.springframework.data.repository.CrudRepository;

import ch.rasc.reactorsandbox.quickstart.spring.domain.Client;

/**
 * @author Jon Brisbin
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
