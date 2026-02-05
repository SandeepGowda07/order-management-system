package com.register.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.register.springboot.model.User;
import com.register.springboot.repository.UserRepository;
import com.register.springboot.service.Userservice;

/**
 * Controller class for handling general user operations and page navigation.
 */
@Controller
public class Controllers {
	@Autowired
	UserRepository userrepo;
	@Autowired
	Userservice userservice;

	/**
	 * Displays the application home page.
	 * 
	 * @return name of the home view template.
	 */
	@GetMapping("/")
	public String Home() {
		return "Home"; // Returns Home.html (fix for case-sensitive Linux)
	}

	/**
	 * Displays the login page. Spring Security handles authentication from here.
	 * 
	 * @return name of the login view template.
	 */
	@GetMapping("/login")
	public String login() {
		return "login"; // Returns login.html
	}

	/**
	 * Displays the User dashboard/profile page.
	 * 
	 * @return name of the User view template.
	 */
	@GetMapping("User")
	public String User() {
		return "User";
	}

	/**
	 * Displays the admin dashboard.
	 * 
	 * @return name of the admin view template.
	 */
	@GetMapping("admin")
	public String Admin() {
		return "admin";
	}

	/**
	 * Displays the registration form.
	 * 
	 * @param user object to bind form data to.
	 * @return name of the register view template.
	 */
	@GetMapping("register")
	public String showsignUpForm(User user) {
		return "register";
	}

	/**
	 * Lists all registered users (restricted to appropriate roles).
	 * 
	 * @param model to pass data to the view.
	 * @return name of the index view template.
	 */
	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("Users", userrepo.findAll());
		return "index";
	}

	/**
	 * Processes the registration of a new user.
	 * 
	 * @param user   the user data to register.
	 * @param result validation results.
	 * @param model  to pass feedback messages to the view.
	 * @return name of the register view template with success or error message.
	 */
	@PostMapping("add")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		} else if (userservice.isUserAlreadyPresent(user)) {
			model.addAttribute("message", "user already Exists");
		} else if (userservice.Age(user)) {
			model.addAttribute("message", "user registered succesfully");
		} else {
			model.addAttribute("message", "age should be greater than 18");
		}
		return "register";

	}

	/**
	 * Updates an existing user's information.
	 * 
	 * @param id     of the user to update.
	 * @param user   updated user data.
	 * @param result validation results.
	 * @param model  to pass feedback messages to the view.
	 * @return name of the redirect view (index or update-user).
	 */
	@PostMapping("update/{id}")
	public String updateUser(@PathVariable("id") int id, @Valid User user, BindingResult result, Model model) {
		String page = "";
		if (result.hasErrors()) {
			page = "update-user";
		} else if (!userservice.Age(user)) {
			model.addAttribute("message", "age should be greater than 18");
			page = "update-user";
		} else {
			model.addAttribute("message", "user updated succesfully");
			model.addAttribute("Users", userrepo.findAll());
			page = "index";
		}
		return page;
	}

	/**
	 * Displays the form to edit a specific user.
	 * 
	 * @param id    of the user to edit.
	 * @param model to pass the user data to the view.
	 * @return name of the update-user view template.
	 */
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		User user = userrepo.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "update-user";
	}

	/**
	 * Deletes a user by ID.
	 * 
	 * @param id    of the user to delete.
	 * @param model to pass the updated list of users to the view.
	 * @return name of the index view template.
	 */
	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		User user = userrepo.findById(id).orElseThrow();
		userrepo.delete(user);
		model.addAttribute("Users", userrepo.findAll());
		return "index";
	}

}
