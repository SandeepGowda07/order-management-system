
package com.register.springboot.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.register.springboot.model.User;

/**
 * Principal object for Spring Security.
 * Wraps the User entity to provide security-related details.
 */
@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails {

	private User user;

	public MyUserDetails(User user) {
		super();
		this.user = user;
	}

	/**
	 * Returns authorities (roles) granted to the user.
	 * Splits the roles string by comma and maps to SimpleGrantedAuthority.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return Arrays.asList(new SimpleGrantedAuthority
		return Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
