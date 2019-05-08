package fr.devnr.jarialtekinapi.dao.factory;

import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;


public abstract class DAOFactory {
	
	// Factories types
	public static final String MARIADB = "MARIADB";


	/**
	 * Creates and return a DAOFactory according to the 'type' argument.
	 * Return null if 'type' is null or is not a valid factory type.
	 * 
	 * @param type the type of DAOFactory to return
	 * @return a DAOFactory of the given type
	 */
	public static DAOFactory getDAOFactory(String type) {
		switch (type.toUpperCase()) {
			case MARIADB:
				return new DAOFactoryDefault();
			default:
				return null;
		}
	}


	public abstract TaskDAO getTaskDAO();
	public abstract TaskPlanningDAO getTaskPlanningDAO();
	public abstract ProjectDAO getProjectDAO();
	
}
