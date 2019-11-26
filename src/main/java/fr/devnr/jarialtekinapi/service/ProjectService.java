package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;

import javax.sql.DataSource;
import java.util.List;


public class ProjectService {

    private final ProjectDAO projectDAO;


    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public ProjectService() {
        DataSource source = DataSourceFactory.getDataSourceFactory().getDataSource();
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        this.projectDAO = daoFactory.getProjectDAO(source);
    }


    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public Project getProject(Long idProject) {
        return projectDAO.getProjectById(idProject);
    }

    public Project getProjectByTask(Long idTask) {
        return projectDAO.getProjectByTask(idTask);
    }

    public List<Task> getProjectTasks(Long idProject) {
        return projectDAO.getProjectTasks(idProject);
    }

    public Project createProject(Project project) {
        return projectDAO.createProject(project);
    }

    public Boolean updateProject(Project project) {
        Project newProject = projectDAO.getProjectById(project.getId());
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        return projectDAO.updateProject(newProject);
    }

    public Boolean deleteProject(Long idProject) {
        return projectDAO.deleteProject(idProject);
    }

}
