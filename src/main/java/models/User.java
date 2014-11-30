/** 
 * User model class.
 *
 * @author: Hubert Chen
 */
package models;

public class User { 

	private String username;
	private String password;
	private String email;

	public User(){}

	/**
	 * Creates a new User
	 * 
	 * @param aUsername 	Username of new User
	 * @param aEmail	E-Mail of new User
	 * @param aPassword	Password of new User
	 */
	public User(String aUsername, String aEmail, String aPassword) {
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
	 * Retrieve the User's username
	 *
	 * @return User's username
	 */
	public String getUsername() { return username; }

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
}
