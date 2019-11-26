package fr.devnr.jarialtekinapi.dao.impl.defaut;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;


public class DAOFactoryDefault extends DAOFactory {

	@Override
	public fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO getTaskDAO(DataSource source) {
		return new TaskDAODefault( source );
	}

	@Override
	public fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO getTaskPlanningDAO(DataSource source) {
		return new TaskPlanningDAODefault( source );
	}

	@Override
	public fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO getProjectDAO(DataSource source) {
		return new ProjectDAODefault( source );
	}

}
