package com.register.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * Entity class representing a User in the system.
 * Includes validation for registration fields.
 */
@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** Unique username for the user */
	@NotBlank(message = "username is compulsory")
	@Pattern(regexp = "[a-zA-Z]+", message = "Name should contain letters")
	private String userName;

	/** Hashed password for security */
	@NotBlank(message = "password is compulsory")
	@Length(min = 5, message = "password should be minimum 5 characters")
	private String password;

	/** User's email address */
	@NotBlank(message = "email is compulsory")
	@Email(message = "Email is invalid")
	private String email;

	/** Date of Birth string (parsed in service) */
	@NotBlank(message = "Dob is compulsory")
	private String dob;

	/** User roles (e.g., ROLE_USER, ROLE_ADMIN) */
	private String roles;

	/** Calculated age based on DOB */
	private int age;

	public User() {
		super();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
