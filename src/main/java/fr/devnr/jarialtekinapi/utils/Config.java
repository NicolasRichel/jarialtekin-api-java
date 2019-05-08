package fr.devnr.jarialtekinapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Config {

    private static final Logger logger = Logger.getLogger("config-logger");

    private static final String DEFAULT_CONFIG_FILE = "/application.properties";
    private static final String DEFAULT_DB_CONFIG_FILE = "/db.properties";


    /**
     * Get a property value from the default configuration file.
     *
     * @param property property to get the value of
     * @return value of the given property
     */
    public static String get(String property) {
        return loadPropertyFile(null).getProperty(property);
    }

    /**
     * Get a property value from a sepcified config file.
     * If the given file name is null then get property from
     * the default configuration file ("application.properties").
     *
     * @param filename file to get property from
     * @param property property to get the value of
     * @return value of the given property (in the specified file)
     */
    public static String get(String filename, String property) {
        return loadPropertyFile(filename).getProperty(property);
    }

    /**
     * Get a set of properties from a specified config file.
     * If the given file name is null then get properties from
     * the default configuration file ("application.properties").
     *
     * @param filename file to get properties from
     * @param properties properties to get the value of
     * @return a Map containing { property, value } pairs
     */
    public static Map<String, String> get(String filename, String[] properties) {
        Map<String, String> propMap = new HashMap<>();
        Properties prop = loadPropertyFile(filename);
        for (String property : properties) {
            propMap.put(property, prop.getProperty(property));
        }
        return propMap;
    }

    /**
     * Load databse properties from the specified file ('filename').
     * If filename is null then load properties from "db.properties".
     * The property file should contain the following fields :
     *   - DB_DRIVER : driver class name for this kind of DB
     *   - DB_URL : full URL of the DB server
     *   - DB_HOST : hostname of the DB server
     *   - DB_PORT : port on which the DB server is reachable
     *   - DB_USERNAME : user to use to connect to the DB
     *   - DB_PASSWORD : password of the user used to connect to the DB
     *   - DB_SCHEMA : DB schema on which queries will be performed
     *
     * @param filename file to load properties from
     * @return a Map containing database properties
     */
    public static Map<String, String> loadDatabaseConfig(String filename) {
        String[] properties = { "DB_DRIVER", "DB_URL", "DB_HOST", "DB_PORT", "DB_USERNAME", "DB_PASSWORD", "DB_SCHEMA" };
        String file = filename!=null ? filename : DEFAULT_DB_CONFIG_FILE;
        return get(file, properties);
    }



    private static Properties loadPropertyFile(String filename) {
        String file = filename!=null ? filename : DEFAULT_CONFIG_FILE;
        Properties props = new Properties();
        try ( InputStream input = Config.class.getResourceAsStream(file) ) {
            props.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while accessing property file : \""+filename+"\" : "+e.getMessage());
            e.printStackTrace();
        }
        return props;
    }

}
