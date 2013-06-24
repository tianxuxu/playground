package ch.rasc.sec.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

public class AdditionalWebAuthenticationDetailsSource implements
		AuthenticationDetailsSource<HttpServletRequest, AdditionalWebAuthenticationDetails> {

	@Override
	public AdditionalWebAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new AdditionalWebAuthenticationDetails(request);
	}

}
