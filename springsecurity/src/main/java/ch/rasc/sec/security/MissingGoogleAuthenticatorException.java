package ch.rasc.sec.security;

import org.springframework.security.core.AuthenticationException;

public class MissingGoogleAuthenticatorException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public MissingGoogleAuthenticatorException(String msg) {
		super(msg);
	}

}
