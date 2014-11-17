/**
 * Main Controller for the web app
 *
 * Handles routing
 *
 * @author Hubert Chen
 */
package controllers;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {
	private ComboPooledDataSource dataSource;

	@RequestMapping("/")
	public String index() { return "index"; }

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "signup"; 
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() {
		return "signin"; 
	}

	/* 
	 * This method is called whenever a Mapping needs to establish
	 * a connection to the database.
	 * 
	 * Will return the datasource if already intialized; otherwise,
	 * we will create the C3P0 datasource
	 */
	private void initDataSource() {
		if(dataSource == null) { // DataSource has not been intialized
			String dbUrl 		= "jdbc:postgresql://localhost/Flashcards";
			String dbUser 		= "test";
			String dbPassword 	= "123";

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
