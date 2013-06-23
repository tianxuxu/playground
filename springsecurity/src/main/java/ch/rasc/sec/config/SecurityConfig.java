package ch.rasc.sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
		  .inMemoryAuthentication()
		  .withUser("user").password("password").roles("USER")
		  .and()
		  .withUser("admin").password("password").roles("USER", "ADMIN");
	}

	@Override
	public void configure(WebSecurityBuilder builder) throws Exception {
		builder.ignoring().antMatchers("/resources/**", "/favicon.ico", "/robots.txt");
	}

	@Override
	protected void configure(HttpConfiguration http) throws Exception {
		http
		  .authorizeUrls()
		  .antMatchers("/", "/sayHello").hasRole("ADMIN")
		  //.anyRequest().hasRole("USER")
		  .anyRequest().authenticated()
		
		  .and()
		  .formLogin()
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