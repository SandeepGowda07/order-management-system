package com.register.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main security configuration class for the application.
 * Configures URL access rules, login/logout behavior, and session management.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomLoginSuccesHandler successHandler;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// ========== SESSION & CACHE CONTROL ==========
				// Prevents back button from showing cached pages
				.headers()
				.cacheControl() // Adds: Cache-Control: no-cache, no-store
				.and()
				.and()

				// ========== URL ACCESS RULES ==========
				.authorizeRequests()
				// Admin only - all admin pages
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/edit/{id}").hasRole("ADMIN")
				.antMatchers("/delete/{id}").hasRole("ADMIN")
				.antMatchers("/list").hasRole("ADMIN")

				// Logged-in users (USER or ADMIN) - products, orders
				.antMatchers("/products/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/order/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/my-orders").hasAnyRole("USER", "ADMIN")
				.antMatchers("/User").hasAnyRole("USER", "ADMIN")

				// Public pages - no login required
				.antMatchers("/register").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/css/**", "/js/**", "/images/**").permitAll()

				.and()

				// ========== LOGIN CONFIG ==========
				.formLogin()
				.loginPage("/login") // Use OUR login.html template
				.permitAll() // Allow everyone to see login page
				.successHandler(successHandler) // Custom redirect after login
				.and()

				// ========== LOGOUT CONFIG ==========
				.logout()
				.logoutSuccessUrl("/") // Redirect to home after logout
				.invalidateHttpSession(true) // Destroy session on logout
				.deleteCookies("JSESSIONID") // Delete session cookie
				.permitAll();

		http.csrf().disable();
	}

}
