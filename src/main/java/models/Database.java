/**
 * A class that handles any transactions involving the database
 *
 * @author Hubert Chen
 */
package models;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public final class Database {
	private static ComboPooledDataSource dataSource;
	private static JdbcTemplate jdbcTemplate;

	private Database(){}

	public static void query() {
		
	}
	
	public static String getPassword(String username) {
		init();

		List<String> results = jdbcTemplate.query(
			"SELECT password FROM users WHERE LOWER(username) = LOWER(?)", 
			new Object[] {username},
			new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet result, int rowNum) throws SQLException {
					return result.getString("password");
				}
			});

		if(results.size() == 1)
			return results.get(0);
		else
			return null;
	}

	/** 
	 * Creates a new User entry in the database
	 * 
	 * @param user User object to be inserted
	 */
	 public static void insertUser(User user) {
		init();

		jdbcTemplate.update(
			"INSERT INTO users(username, email, password) VALUES(?, ?, ?)",
			new Object[] {
				user.getUsername(),
				user.getEmail(),
				user.getPassword()
			}
		);
	 }

	/**
	 * Creates a new Folder entry in the database
	 * 
	 * @param folder Folder object to be inserted
	 * @param userId User ID who owns the folder
	 */
	public static void insertFolder(Folder folder, int userId) {
		init();

		jdbcTemplate.update(
			"INSERT INTO folders(userid, name) VALUES(?, ?)", 
			new Object[] {
				userId, 
				folder.getName() 
			}
		);
	}

	/**
	 * Creates a new Deck entry in the database
	 * 
	 * @param folder Folder object to be inserted
	 * @param userId User ID who owns the folder
	 */
	public static void insertDeck(Deck deck) {
		init();

		jdbcTemplate.update(
			"INSERT INTO decks(name, description, folderid) VALUES(?, ?, ?)", 
			new Object[] {
				deck.getName(), 
				deck.getDescription(),
				deck.getFolderId()
			}
		);
	}

	/** 
	 * Creates a new flashcard entry in the database
	 *
	 * @param flashcard Flashcard object to be inserted
	 */
	 public static void insertFlashcard(Flashcard flashcard) {
	 	init();

		jdbcTemplate.update(
			"INSERT INTO cards(deckid, front, back) VALUES(?, ?, ?)",
			new Object[] {
				flashcard.getDeckId(), 
				flashcard.getFront(),
				flashcard.getBack()
			}
		);
	 }

	/** 
	 * Creates a new flashcard entry in the database if it has picture
	 *
	 * @param flashcard Flashcard object to be inserted
	 * @param hasFile True if there is picture, else false
	 */
	 public static void insertFlashcard(Flashcard flashcard, String fileName) {
	 	init();

		jdbcTemplate.update(
			"INSERT INTO cards(deckid, front, back, picture) VALUES(?, ?, ?, ?)",
			new Object[] {
				flashcard.getDeckId(), 
				flashcard.getFront(),
				flashcard.getBack(),
				fileName
			}
		);
	 }

	/**
	 * Removes a flashcard entry 
	 *
	 * @param id Flashcard ID to be deleted
	 */
	public static void removeFlashcard(int id) {
		init();

		jdbcTemplate.update(
			"DELETE FROM cards WHERE id = ?", 
			new Object[] {
				id
			}
		);
	}

	/**
	 * Find a user given a username.
	 * 
	 * This should return a list of only one User as usernames
	 * are unique.
	 * 
	 * @param username Unique username to be searched
	 * @return List of Users who match the given username
	 */
	public static List<User> findUser(String username) {
		init();

		return jdbcTemplate.query(
			"SELECT * FROM users WHERE LOWER(username) = LOWER(?)", 
			new Object[] {username},
			new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet result, int rowNum) throws SQLException {
					return new User(
						result.getString("username"), 
						result.getString("password"), 
						result.getString("email"));
				}
			});
	}

	/**
	 * Determines if a User exists in database
	 *
	 * @param username Unique username to be searched
	 * @return true if user exists; otherwise, false.
	 */
	public static boolean isUser(String username) {
		if(findUser(username).isEmpty())
			return false;
		return true;
	}

	/**
	 * Updates the User entry in the database
	 *
	 * @param user User object to be updated
	 */
	 public static void updateUser(User user) {
			init();

			jdbcTemplate.update(
				"UPDATE users SET username = ?, email = ?, password = ?",
				new Object[] {
					user.getUsername(),
					user.getEmail(), 
					user.getPassword()
				}
			);
	 }

	/** 
	 * Gets all the flashcards associated with a deck
	 *
	 * @param int deckId
	 * @return List of flashcards
	 */
	public static List<Flashcard> getFlashcards(int deckId) {
		init();

		return jdbcTemplate.query(
			"SELECT * FROM cards WHERE deckid = ?",
			new Object[] {deckId}, 
			new RowMapper<Flashcard>() {
				@Override
				public Flashcard mapRow(ResultSet result, int rowNum) throws SQLException {
					Flashcard flashcard = new Flashcard(result.getString("front"), result.getString("back"));
					flashcard.setId(Integer.parseInt(result.getString("id")));
					flashcard.setDeckId(Integer.parseInt(result.getString("deckid")));
					flashcard.setPicture(result.getString("picture"));

					return flashcard;
				}
			}
		);	
	}
	
	/**
	 * Gets all the folders associated with an username
	 * 
	 * @param username User's username
	 * @return List of folders
	 */
	public static List<Folder> getFolders(String username) {
		init();

		int userId = getUserId(username);

		return jdbcTemplate.query(
				"SELECT * FROM folders WHERE userId = ?",
				new Object[] {userId}, 
				new RowMapper<Folder>() {
					@Override 
					public Folder mapRow(ResultSet result, int rowNum) throws SQLException {
						Folder tempFolder = new Folder(result.getString("name"));
						tempFolder.setDecks(getDecks(result.getInt("id")));
						tempFolder.setId(result.getInt("id"));

						return tempFolder;
					}
				}
		);
	}

	/** 
	 * Gets all the Decks associated with a folder ID
	 *
	 * @param folderId The ID of the folder
	 * @return List of decks
	 */
	public static List<Deck> getDecks(int folderId) {
		init();

		return jdbcTemplate.query(
			"SELECT * FROM decks where folderid = ?",
			new Object[] {folderId},
			new RowMapper<Deck>() { 
				@Override
				public Deck mapRow(ResultSet result, int rowNum) throws SQLException {
					return new Deck(
						result.getInt("id"),
						result.getString("name"),
						result.getString("description")
					);
				}
			}
		);
	}

	/**
	 * Gets the Deck associated with a Deck ID
	 *
	 * @param deckId The ID of the deck
	 * @return The Deck object associated with the ID
	 */
	public static Deck getDeck(int deckId) {
		init();

		List<Deck> deck = jdbcTemplate.query(
			"SELECT * FROM decks where id = ?",
			new Object[] { deckId}, 
			new RowMapper<Deck>() {
				@Override
				public Deck mapRow(ResultSet result, int rowNum) throws SQLException {
					return new Deck(
						result.getInt("id"),
						result.getString("name"), 
						result.getString("description")
					);
				}
			}
		);

		return deck.get(0);
	}
	
	/** 
	 * Gets the user ID when given a username
	 * 
	 * @param username Username of the User
	 * @return User ID
	 */
	public static int getUserId(String username) {
		init();

		List<Integer> userId = jdbcTemplate.query(
			"SELECT id FROM users where username = (?)", 
			new Object[] {username},
			new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet result, int rowNum) throws SQLException { 
					return new Integer(
						result.getInt("id")
					);
				} 
			}	
		);	

		return userId.get(0).intValue();
	}

	/**
	 * Initalizes both Spring's JDBC template and the C3P0 datasource.
	 *
	 * Reference to initJdbcTemp() for easier readability. Only initJdbcTemp()
	 * is called because the method also calls the initDataSource() method.
	 */
	private static void init() { initJdbcTemp(); }

	/**
	 * Ensures the Spring JDBC template is initalized.
	 *
	 * Initalizes the JDBC template if it hasn't been already;
	 * Otherwise, return and do nothing.
	 */
	private static void initJdbcTemp() {
		if(jdbcTemplate == null) { // JDBC template has not been initalized
			initDataSource(); // Must be called first
			jdbcTemplate = new JdbcTemplate(dataSource);
		} else {
			// JDBC template is already initalzied
			return;
		}
	}

	/**
	* Ensures the C3P0 datasource is initalized.
	* 
	* Initalizes the datasource if it hasn't been already; Otherwise, 
	* returns and does nothing.
	*/
	private static void initDataSource() {
		if(dataSource == null) { // DataSource has not been intialized
			String dbUrl        = "jdbc:postgresql://localhost/flashcards";
			String dbUser       = "test";
			String dbPassword   = "123";

			dataSource = new ComboPooledDataSource();

			try {
				dataSource.setDriverClass("org.postgresql.Driver");
			} catch (PropertyVetoException e) {
				System.out.println("ERROR: Unable to set the PostgreSQL JDBC driver");
				System.out.println("We might be in big trouble Hubert.");
				System.exit(1);
			}

			dataSource.setJdbcUrl(dbUrl);
			dataSource.setUser(dbUser);
			dataSource.setPassword(dbPassword);
		} else {
			/*DataSource has been intialized. Refer to the dataSource
			 * instance variable.
			 */
				return;
		}
	}
}
