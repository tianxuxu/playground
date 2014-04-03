package ch.rasc.sec.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TwoFactorAuthenticationConfigurer extends
UserDetailsAwareConfigurer<AuthenticationManagerBuilder, UserDetailsService> {

	private TwoFactorAuthenticationProvider provider = new TwoFactorAuthenticationProvider();

	private final UserDetailsService userDetailsService;

	protected TwoFactorAuthenticationConfigurer(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		provider.setUserDetailsService(userDetailsService);
	}

	public TwoFactorAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
		provider.setPasswordEncoder(passwordEncoder);
		return this;
	}
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		provider = postProcess(provider);
		builder.authenticationProvider(provider);
	}

	@Override
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

}
