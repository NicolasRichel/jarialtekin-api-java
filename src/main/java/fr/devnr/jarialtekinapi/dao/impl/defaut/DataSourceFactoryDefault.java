package fr.devnr.jarialtekinapi.dao.impl.defaut;

import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DataSourceFactoryDefault extends DataSourceFactory {

    private static final String HOST = "DB_HOST";
    private static final String PORT = "DB_PORT";
    private static final String SCHEMA = "DB_SCHEMA";
    private static final String USER = "DB_USERNAME";
    private static final String PASSWORD = "DB_PASSWORD";


    public DataSource getDataSource() {
        MariaDbDataSource ds = null;
        try {

            ds = new MariaDbDataSource(
                props.get(HOST),
                Integer.parseInt(props.get(PORT)),
                props.get(SCHEMA)
            );
            ds.setUser( props.get(USER) );
            ds.setPassword( props.get(PASSWORD) );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

}
