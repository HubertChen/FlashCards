/**
 * Main Controller for the web app
 *
 * Handles routing
 *
 * @author Hubert Chen
 */
package controllers;

import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {

	@RequestMapping("/")
	public String index() { return "index"; }

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup"; 
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupSubmit(@ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		User newUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
		return "result";	
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Model model) {
		model.addAttribute("user", new User());
		return "signin"; 
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signinSubmit(@ModelAttribute User user, Model mode) {
		model.addAttribute("user", user);
		User.check(user.getUsername(), user.getPassword());
		return "result2";
	}
}
