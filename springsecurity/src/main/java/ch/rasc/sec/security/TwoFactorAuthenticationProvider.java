package ch.rasc.sec.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class TwoFactorAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		super.additionalAuthenticationChecks(userDetails, authentication);
		
		if (authentication.getDetails() instanceof AdditionalWebAuthenticationDetails) {
			String googleCode = ((AdditionalWebAuthenticationDetails)authentication.getDetails()).getCode();			
			System.out.println("google code: " + googleCode);
		}
		

	}

}
