package com.register.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.register.springboot.model.User;

/**
 * Repository interface for User entity.
 * Provides standard CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	/**
	 * Finds a user by their username.
	 * 
	 * @param userName to search for.
	 * @return User object if found, null otherwise.
	 */
	User findByUserName(String userName);

	/**
	 * Counts users that have a specific role.
	 * 
	 * @param role to search for (e.g., "ROLE_ADMIN").
	 * @return count of users with that role.
	 */
	long countByRolesContaining(String role);
}
