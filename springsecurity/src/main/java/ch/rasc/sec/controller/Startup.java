package ch.rasc.sec.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.sec.entity.QRole;
import ch.rasc.sec.entity.QUser;
import ch.rasc.sec.entity.Role;
import ch.rasc.sec.entity.User;

import com.google.common.collect.Sets;
import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		Role adminRole = null;
		Role userRole = null;

		if (new JPAQuery(entityManager).from(QRole.role).notExists()) {
			adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			entityManager.persist(adminRole);

			userRole = new Role();
			userRole.setName("ROLE_USER");
			entityManager.persist(userRole);
		} else {
			adminRole = new JPAQuery(entityManager).from(QRole.role).where(QRole.role.name.eq("ROLE_ADMIN"))
					.singleResult(QRole.role);
			userRole = new JPAQuery(entityManager).from(QRole.role).where(QRole.role.name.eq("ROLE_USER"))
					.singleResult(QRole.role);
		}

		if (new JPAQuery(entityManager).from(QUser.user).notExists()) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);

			adminUser.setRoles(Sets.newHashSet(adminRole));

			entityManager.persist(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setLocale("de");

			normalUser.setPasswordHash(passwordEncoder.encode("user"));
			normalUser.setEnabled(true);

			normalUser.setRoles(Sets.newHashSet(userRole));

			entityManager.persist(normalUser);
		}

	}

}
