package fr.devnr.jarialtekinapi.dao.factory;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.impl.ProjectDAODefaultImpl;
import fr.devnr.jarialtekinapi.dao.impl.TaskDAODefaultImpl;
import fr.devnr.jarialtekinapi.dao.impl.TaskPlanningDAODefaultImpl;
import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;

public class DAOFactoryDefault extends DAOFactory {
	
	/**
	 * Creates and return a data source using DataSourceFactory
	 * 
	 * @return the created data source
	 */
	private static DataSource createDataSource() {
		return DataSourceFactory.getMariadbDataSource(null);
	}
	
	
	// Methods to create DAOs
	
	public TaskDAO getTaskDAO() {
		return new TaskDAODefaultImpl(createDataSource());
	}
	
	public TaskPlanningDAO getTaskPlanningDAO() {
		return new TaskPlanningDAODefaultImpl(createDataSource());
	}
	
	public ProjectDAO getProjectDAO() {
		return new ProjectDAODefaultImpl(createDataSource());
	}

}
