/**
 * Main Controller for the web app
 *
 * Handles routing
 *
 * @author Hubert Chen
 */
package controllers;

import models.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Main {

	@RequestMapping("/")
	public String index(HttpSession session, Model model) { 
		if(session.getAttribute("username") == null)
			return "index"; 
		else
			return home(session, model);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model, HttpSession session) {
		if(session.getAttribute("username") == null) {
			model.addAttribute("user", new User());
			return "signup"; 
		} else
			return home(session, model);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupForm(@ModelAttribute User user, Model model, HttpSession session) {
		// Takes the user's inputted password and hashes it with salt
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		
		if(user.save()) {
			session.setAttribute("username", user.getUsername());
			return home(session, model);
		} else { // Username is taken, redirect back to /signup
			return "signup";
		}
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Model model, HttpSession session) {
		if(session.getAttribute("username") == null){
			model.addAttribute("user", new User());
			return "signin"; 
		} else 
			return home(session, model);
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signinForm(@ModelAttribute User user, Model model, HttpSession session) {
		String userPassword = user.getPassword();
		String dbPassword = Database.getPassword(user.getUsername());
		String username = user.getUsername();

		if(userPassword == null || dbPassword == null || username == null) {
			return "signin";
		}
		
		boolean isCorrectPassword = BCrypt.checkpw(userPassword, dbPassword);

		if(isCorrectPassword) {
			session.setAttribute("username", username);
			return home(session, model);
		} else {
			return "signin";
		}
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signOut(HttpSession session) {
		if(session.getAttribute("username") == null) {
			return "index";
		} else {
			session.setAttribute("username", null);
			return "index";
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		if(session.getAttribute("username") == null)
			return "index";
		else {
			User user = User.getUser((String)session.getAttribute("username"));
			model.addAttribute("folder", new Folder());
			model.addAttribute("deck", new Deck());

			if(user != null)
				model.addAttribute("folders", user.getFolders());
			
			return "home";
		}
	}

	@RequestMapping(value = "/addFolder", method = RequestMethod.POST)
	public String addFolderForm(@ModelAttribute Folder folder, HttpSession session, Model model) {
		if(session.getAttribute("username") == null)
			return "index";

		int userId = User.getUserId((String) session.getAttribute("username"));
		folder.save(userId);
		
		return home(session, model);
	}

	@RequestMapping(value = "/addDeck", method = RequestMethod.POST)
	public String addDeckForm(@ModelAttribute Deck deck, HttpSession session, Model model) {
		if(session.getAttribute("username") == null)
			return "index";

		deck.save();

		return home(session, model);
	}

	@RequestMapping(value = "/deck", params = {"id"}, method = RequestMethod.GET)
	public String deck(@RequestParam(value = "id") int id, HttpSession session, Model model) {
		if(session.getAttribute("username") == null)
			return "index";

		model.addAttribute("deckId", id);
		model.addAttribute("deck", Database.getDeck(id));
		model.addAttribute("flashcards", Database.getFlashcards(id));
		model.addAttribute("flashcard", new Flashcard());

		return "deck";
	}

	@RequestMapping(value = "/addFlashcard", params = {"deckId"}, method = RequestMethod.POST)
	public String addFlashcard(
			@RequestParam("file") MultipartFile file,
			@RequestParam("deckId") int deckId,
			@ModelAttribute Flashcard flashcard, 
			HttpSession session, 
			Model model) {

		if(session.getAttribute("username") == null)
			return "index";

		flashcard.setDeckId(deckId);

		if(!file.isEmpty()) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");

				byte[] bytes = file.getBytes();
				byte[] hash = md.digest(bytes);

				File pictureFile = new File("public/pictures/" + hash);

				BufferedOutputStream stream = 
					new BufferedOutputStream( 
						new FileOutputStream(
							pictureFile
						)
					);

				stream.write(bytes);
				stream.close();
	
				System.out.println(pictureFile.getName());
				Database.insertFlashcard(flashcard, pictureFile.getName());
					
				return deck(deckId, session, model);
			} catch(Exception e) {
				return deck(deckId, session, model);	
			}
		}			
		Database.insertFlashcard(flashcard);
		return deck(deckId, session, model);
	}

	@RequestMapping(value = "/removeFlashcard", params = {"flashcardId", "deckId"}, method = RequestMethod.POST)
	public String removeFlashcard(
			@RequestParam("flashcardId") int flashcardId, 
			@RequestParam("deckId") int deckId, 
			HttpSession session, 
			Model model) {

		if(session.getAttribute("username") == null) 
			return "index";

		Database.removeFlashcard(flashcardId);

		return deck(deckId, session, model);
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "about";
	}
}
