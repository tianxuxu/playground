package ch.rasc.sec.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.sec.entity.QUser;
import ch.rasc.sec.entity.User;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class UserAuthenticationErrorHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof String) {
			User user = new JPAQuery(entityManager).from(QUser.user).where(QUser.user.userName.eq((String) principal))
					.singleResult(QUser.user);
			if (user != null) {
				if (user.getFailedLogins() == null) {
					user.setFailedLogins(1);
				} else {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}

				if (user.getFailedLogins() > 10) {
					user.setLockedOut(DateTime.now().plusMinutes(10));
				}

			} else {
				LogManager.getLogger(UserAuthenticationErrorHandler.class).error("Unknown user login attempt: {}",
						principal);
			}
		}
	}
}