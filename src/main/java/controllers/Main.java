/**
 * Main Controller for the web app
 *
 * Handles routing
 *
 * @author Hubert Chen
 */
package controllers;

import models.User;
import models.Database;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {

	@RequestMapping("/")
	public String index(HttpSession session) { 
		if(session.getAttribute("signedIn") == null)
			return "index"; 
		else
			return "home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model, HttpSession session) {
		if(session.getAttribute("signedIn") == null) {
			model.addAttribute("user", new User());
			return "signup"; 
		} else
			return "home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupSubmit(@ModelAttribute User user, Model model, HttpSession session) {
		// Takes the user's inputted password and hashes it with salt
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		
		if(user.save()) {
			session.setAttribute("signedIn", true);
			return "home";
		} else { // Username is taken, redirect back to /signup
			return "signup";
		}
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Model model, HttpSession session) {
		if(session.getAttribute("signedIn") == null){
			model.addAttribute("user", new User());
			return "signin"; 
		} else 
			return "home";
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signinSubmit(@ModelAttribute User user, Model model, HttpSession session) {
		String userPassword = user.getPassword();
		String dbPassword = Database.getPassword(user.getUsername());
		String username = user.getUsername();

		if(userPassword == null || dbPassword == null || username == null) {
			return "signin";
		}
		
		boolean isCorrectPassword = BCrypt.checkpw(userPassword, dbPassword);

		if(isCorrectPassword) {
			session.setAttribute("signedIn", true);
			return "home";
		} else {
			return "signin";
		}
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signOut(HttpSession session) {
		if(session.getAttribute("signedIn") == null) {
			return "index";
		} else {
			session.setAttribute("signedIn", null);
			return "index";
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession session) {
		if(session.getAttribute("signedIn") == null)
			return "index";
		else
			return "home";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "about";
	}
}
