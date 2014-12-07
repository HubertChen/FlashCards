/** 
 * User model class.
 *
 * @author: Hubert Chen
 */
package models;

import java.util.List;
import java.util.ArrayList;

public class User { 

	private String username;
	private String password;
	private String email;
	private List<Folder> folders;

	public User(){}

	/**
	 * Creates a new User
	 * 
	 * @param aUsername 	Username of new User
	 * @param aEmail	E-Mail of new User
	 * @param aPassword	Password of new User
	 */
	public User(String aUsername, String aEmail, String aPassword) {
		folders = Database.getFolders(aUsername);
	}
	
	/**
	 * Saves a new User to the database
	 *
	 * This method should only be called upon User registration.
	 * 
	 * @return false if username is already in database; otherwise true.
	 */
	public boolean save() {
		if(Database.isUser(username)) {
			// Already a user within database
			return false;
		} else { // User is not within database, create new entry
			Database.insertUser(this);
			return true;
		}			
	}

	/**
	 * Saves the current state of the User to the database
	 *
	 * This method should not be called when creating a new user
	 * because it might overwrite a previous user
	 */
	public void saveOrUpdate() {
		if(Database.isUser(username)) { 
			// Already a user within database, just update entry
			Database.updateUser(this);
		} else { // User is not within database, create new entry
			Database.insertUser(this);
		}
	}

	/**
	 * Puts the User object condensed into a String
	 *
	 * @return Concatenated values of attributes
	 */
	public String toString() {
		return "Username: " + this.username + 
			", Password: " + this.password + 
			", E-Mail: " + this.email;
	}

	/**
	 * Retrieve the User's E-Mail
	 * 
	 * @return User's e-mail
	 */
	public String getEmail() { return email; }

	/** 
	 * Retrieve the User's password
	 *
	 * @return User's password
	 */
	public String getPassword() { return password; }

	/**
	 * Returns a User object of the given username
	 * 
	 * @param username Username of the desired User
	 * @return User object that has the given username
	 */
	public static User getUser(String username) {
		List<User> users = Database.findUser(username);

		if(users.size() == 1)
			return users.get(0);

		return null;
	}

	/**
	 * Returns the user ID given the username
	 * 
	 * @param username Username of the User
	 * @return ID of the username
	 */
	public static int getUserId(String username) {
		return Database.getUserId(username);
	}

	/**
	 * Retrieve the User's username
	 *
	 * @return User's username
	 */
	public String getUsername() { return username; }

	/**
	 * Retrieve the User's folders
	 * 
	 * @return A ArrayList of folders
	 */
	public List<Folder> getFolders() { return folders; }

	/**
	 * Sets the User's new E-Mail
	 * 
	 * @param newEmail User's new E-Mail address
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	/**
	 * Sets the User's password
	 * 
	 * @param newPassword User's new password in non-hashed form 
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	/**
	 * Sets the User's username
	 * 
	 * @param newUsername User's new username
	 */
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Sets the User's folders
	 *
	 * @param newFolders An ArrayList of new Folders
	 */
	public void setFolders(ArrayList<Folder> newFolders) { 
		this.folders = newFolders;
	}
}
