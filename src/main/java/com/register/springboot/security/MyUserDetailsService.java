
package com.register.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.register.springboot.model.User;
import com.register.springboot.repository.UserRepository;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Fetches user data from the database using UserRepository.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userrepository;

	/**
	 * Loads user by username for authentication.
	 * 
	 * @param userName the username to search for.
	 * @return UserDetails object for the user.
	 * @throws UsernameNotFoundException if user is not found.
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userrepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("username not found");
		}
		return new MyUserDetails(user);
	}

}
