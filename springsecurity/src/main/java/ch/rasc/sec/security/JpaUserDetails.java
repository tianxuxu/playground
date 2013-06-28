package ch.rasc.sec.security;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.rasc.sec.entity.Role;
import ch.rasc.sec.entity.User;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final String secret;

	private final boolean enabled;

	private final Long userDbId;

	private final boolean locked;

	private final boolean expired;

	public JpaUserDetails(User user) {
		this.userDbId = user.getId();

		this.password = user.getPasswordHash();
		this.username = user.getUserName();
		this.secret = user.getSecret();
		this.enabled = user.isEnabled();

		if (user.getLockedOut() != null && user.getLockedOut().isAfter(DateTime.now())) {
			locked = true;
		} else {
			locked = false;
		}

		if (user.getExpirationDate() != null && DateTime.now().isAfter(user.getExpirationDate())) {
			expired = true;
		} else {
			expired = false;
		}

		Builder<GrantedAuthority> builder = ImmutableSet.builder();
		for (Role role : user.getRoles()) {
			builder.add(new SimpleGrantedAuthority(role.getName()));
		}

		this.authorities = builder.build();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public Long getUserDbId() {
		return userDbId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getSecret() {
		return secret;
	}

}
