package fr.devnr.jarialtekinapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Config {

    private static final String CONFIG_FILE = "application.properties";
    private static final String DATABASE_IMPL = "database.implementation";
    private static final String DATASOURCE_IMPL = "datasource.implementation";
    private static final String DATASOURCE_PROPERTY_FILE = "datasource.propertyFile";
    private static final String GRAPHQL_SCHEMA = "graphql.schema";

    private static class ConfigHolder {
        public static final Config config = new Config();
    }

    private Properties props;

    private Config() {
        props = loadPropertyFile(CONFIG_FILE);
    }


    public static Config getConfig() {
        return ConfigHolder.config;
    }


    public String get(String propName) {
        return props.getProperty( propName );
    }

    public Map<String, String> get(String[] propNames) {
        Map<String, String> propMap = new HashMap<>();
        for (String propName : propNames) {
            propMap.put(propName, props.getProperty( propName ));
        }
        return propMap;
    }

    public String getGraphQLSchema() {
        return props.getProperty( GRAPHQL_SCHEMA );
    }

    public String getDatabaseImpl() {
        return props.getProperty( DATABASE_IMPL ).toUpperCase();
    }

    public String getDatasourceImpl() {
        return props.getProperty( DATASOURCE_IMPL ).toUpperCase();
    }

    public Map<String, String> getDataSourceProperties() {
        Map<String, String> propMap = new HashMap<>();
        Properties dataSourceProps = loadPropertyFile( props.getProperty(DATASOURCE_PROPERTY_FILE) );
        for (Map.Entry<Object, Object> e : dataSourceProps.entrySet()) {
            propMap.put((String) e.getKey(), (String) e.getValue());
        }
        return propMap;
    }


    private static Properties loadPropertyFile(String filename) {
        Properties props = new Properties();
        try ( InputStream input = Config.class.getResourceAsStream("/"+filename) ) {
            props.load( input );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}
