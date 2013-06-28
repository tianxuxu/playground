package ch.rasc.sec.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

public class TwoFactorAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		super.additionalAuthenticationChecks(userDetails, authentication);

		if (authentication.getDetails() instanceof AdditionalWebAuthenticationDetails) {
			Integer googleAuthenticatorCode = ((AdditionalWebAuthenticationDetails) authentication.getDetails())
					.getCode();
			String secret = ((JpaUserDetails) userDetails).getSecret();

			if (StringUtils.hasText(secret)) {
				if (googleAuthenticatorCode != null) {
					try {
						if (!GoogleAuthenticatorUtil.verifyCode(secret, googleAuthenticatorCode, 2)) {
							throw new BadCredentialsException("Google Authenticator Code is not valid");
						}
					} catch (InvalidKeyException | NoSuchAlgorithmException e) {
						throw new InternalAuthenticationServiceException("google authenticator code verify failed", e);
					}

				} else {
					throw new MissingGoogleAuthenticatorException("Google Authenticator Code is mandatory");
				}

			}
		}

	}

}
