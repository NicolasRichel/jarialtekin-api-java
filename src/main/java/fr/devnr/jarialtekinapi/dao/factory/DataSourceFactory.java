package fr.devnr.jarialtekinapi.dao.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;

public class DataSourceFactory {
	
	/**
	 * Creates and return a MariaDbDataSource that is set with respect to 
	 * the properties declared in the given file ('dbPropertiesFile').
	 * 
	 * @param dbPropertiesFile file to load DB properties from
	 * @return a new MariaDbDataSource
	 */
	public static DataSource getMariadbDataSource(String dbPropertiesFile) {
		MariaDbDataSource ds = null;
		try {
			Map<String, String> props = loadDBProperties(dbPropertiesFile);
			
			ds = new MariaDbDataSource(
				props.get("HOST"),
				Integer.valueOf(props.get("PORT")),
				props.get("DB")
			);
			
			ds.setUser(props.get("USR"));
			ds.setPassword(props.get("PWD"));
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return ds;
	}
	
	/**
	 * Load databse properties from the specified file ('filename').
	 * If filename is null then load properties from "db.properties".
	 * The property file should contain the following fields :
	 *   - DB_DRIVER ["DRIVER"] : driver class name for this kind of DB
	 *   - DB_URL ["URL"] : full URL of the DB server
	 *   - DB_HOST ["HOST"] : hostname of the DB server
	 *   - DB_PORT ["PORT"] : port on which the DB server is reachable
	 *   - DB_USERNAME ["USR"] : user to use to connect to the DB
	 *   - DB_PASSWORD ["PWD"] : password of the user used to connect to the DB
	 *   - DB_SCHEMA ["DB"] : DB schema on which queries will be performed
	 * 
	 * Note : the strings indicated between brackets are the keys of each
	 * property in the returned Map of properties.
	 * 
	 * @param filename file to load the properties from
	 * @return a Map that contain the database properties
	 * @throws IOException if an error occurs while accessing the property file
	 */
	private static Map<String, String> loadDBProperties(String filename) throws IOException {
		String file = filename!=null ? filename : "/db.properties";
		Properties props = new Properties();
		InputStream input = DataSourceFactory.class.getResourceAsStream(file);
		props.load(input);
		input.close();
		Map<String, String> propMap = new HashMap<>();
		propMap.put("DRIVER", props.getProperty("DB_DRIVER"));
		propMap.put("URL", props.getProperty("DB_URL"));
		propMap.put("HOST", props.getProperty("DB_HOST"));
		propMap.put("PORT", props.getProperty("DB_PORT"));
		propMap.put("USR", props.getProperty("DB_USERNAME"));
		propMap.put("PWD", props.getProperty("DB_PASSWORD"));
		propMap.put("DB", props.getProperty("DB_SCHEMA"));
		return propMap;
	}

}
