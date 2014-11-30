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
