/** 
 * User model class.
 *
 * @author: Hubert Chen
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class User { 

	private long id;
	private String username;
	private String password;
	private String confirmPassword;
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
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flashcard", "root", "123");
			Statement state = (Statement) con.createStatement();
			state.executeUpdate("insert into User(username, email, password) values('" + aUsername + "', '" + aEmail + "', '" + aPassword + "');");
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
	}

	/**
	 * 
	 */
	public static void check(String aUsername, String aPassword) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flashcard", "root", "123");
			Statement state = (Statement) con.createStatement();

		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
	}

	/** 
	 * Retrieve the User's password
	 *
	 * @return User's password
	 */
	public String getPassword() { return password; }

	/**
	 * Retrieve the User's confirmation password
	 *
	 * @return User's confirmation password 
	 */
	public String getConfirmPassword() { return confirmPassword; }

	/**
	 * Retrieve the User's username
	 *
	 * @return User's username
	 */
	public String getUsername() { return username; }

	/**
	 * Retrieve the User's E-Mail
	 * 
	 * @return User's e-mail
	 */
	public String getEmail() { return email; }

	/**
	 * Sets the User's password
	 * 
	 * @param newPassword User's new password in non-hashed form 
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	/**
	 * Sets the User's confirmation password
	 *
	 * @param newPassword User's new confirmation password in non-hashed form
	 */
	public void setConfirmPassword(String newPassword) {
		this.confirmPassword = newPassword;
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
	 * Sets the User's new E-Mail
	 * 
	 * @param newEmail User's new E-Mail address
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	public String toString() {
		return "Username: " + this.username + ", Password: " + this.password + ", Password2: " + this.confirmPassword 
			+ ", E-Mail: " + this.email;
	}
}
