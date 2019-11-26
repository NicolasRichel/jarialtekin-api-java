package fr.devnr.jarialtekinapi.dao.factory;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.impl.defaut.DAOFactoryDefault;
import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.utils.Config;


public abstract class DAOFactory {

    // DAO implementations
    public static final String DEFAULT = "DEFAULT";
    public static final String MARIADB = "MARIADB";
    public static final String STANDARD = "STANDARD";


    public static DAOFactory getDAOFactory() {
        switch ( Config.getConfig().getDatabaseImpl() ) {
            case DEFAULT:
            case MARIADB:
            case STANDARD:
            default:
                return new DAOFactoryDefault();
        }
    }


    public abstract ProjectDAO getProjectDAO(DataSource source);
    public abstract TaskDAO getTaskDAO(DataSource source);
    public abstract TaskPlanningDAO getTaskPlanningDAO(DataSource source);

}
