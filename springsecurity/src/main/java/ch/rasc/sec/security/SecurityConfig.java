package ch.rasc.sec.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    
	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		TwoFactorAuthenticationProvider authenticationProvider = new TwoFactorAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		auth.add(authenticationProvider);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurityBuilder builder) throws Exception {
		builder.ignoring().antMatchers("/resources/**", "/favicon.ico", "/robots.txt");
	}

	@Override
	protected void configure(HttpConfiguration http) throws Exception {
		http
		  .authorizeUrls()
		  .antMatchers("/sayHello").hasRole("ADMIN")
		  .anyRequest().authenticated()

		  .and()
		    .formLogin()
		    .authenticationDetailsSource(new AdditionalWebAuthenticationDetailsSource())
		    .loginPage("/login.jsp")
		    .failureUrl("/login.jsp?error")		    
		    .permitAll()
		    
	      .and()
	        .logout()
	        .logoutSuccessUrl("/login.jsp?logout")
	        .deleteCookies("JSESSIONID")
	        .permitAll();
	}

}