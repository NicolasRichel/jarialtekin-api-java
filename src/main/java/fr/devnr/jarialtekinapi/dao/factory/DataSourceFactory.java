package fr.devnr.jarialtekinapi.dao.factory;

import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.utils.Config;
import org.mariadb.jdbc.MariaDbDataSource;


public class DataSourceFactory {

	/**
	 * Creates and return a MariaDbDataSource that is set with respect to 
	 * the properties declared in the given file ('dbConfigFile').
	 * 
	 * @param dbConfigFile file to load DB configuration from
	 * @return a new MariaDbDataSource
	 */
	public static DataSource getMariadbDataSource(String dbConfigFile) {
		MariaDbDataSource ds = null;
		try {
			Map<String, String> props = Config.loadDatabaseConfig(dbConfigFile);
			
			ds = new MariaDbDataSource(
				props.get("DB_HOST"),
				Integer.valueOf(props.get("DB_PORT")),
				props.get("DB_SCHEMA")
			);
			
			ds.setUser(props.get("DB_USERNAME"));
			ds.setPassword(props.get("DB_PASSWORD"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

}
