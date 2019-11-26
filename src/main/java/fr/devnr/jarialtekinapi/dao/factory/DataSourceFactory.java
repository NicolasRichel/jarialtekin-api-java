package fr.devnr.jarialtekinapi.dao.factory;

import fr.devnr.jarialtekinapi.dao.impl.defaut.DataSourceFactoryDefault;
import fr.devnr.jarialtekinapi.utils.Config;

import javax.sql.DataSource;
import java.util.Map;


public abstract class DataSourceFactory {

    // DataSource implementations
    public static final String DEFAULT = "DEFAULT";
    public static final String MARIADB = "MARIADB";

    protected static Map<String, String> props;


    public static DataSourceFactory getDataSourceFactory() {
        props = Config.getConfig().getDataSourceProperties();
        switch ( Config.getConfig().getDatasourceImpl() ) {
            case DEFAULT:
            case MARIADB:
            default:
                return new DataSourceFactoryDefault();
        }
    }


    public abstract DataSource getDataSource();

}
