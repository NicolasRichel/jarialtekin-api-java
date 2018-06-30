package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DAOFactoryDefault;
import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private final ProjectDAO projectDAO;

    // Constructors
    // ============

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public ProjectService(String daoImpl) {
        DAOFactory factory = new DAOFactoryDefault();
        this.projectDAO = factory.getProjectDAO();
    }


    // ==============
    // Public Methods
    // ==============

    public List<ProjectDTO> getAllProjectsDTO() {
        return projectDAO.getAllProjects()
            .stream()
            .map(p -> new ProjectDTO(p.getId(), p.getName(), p.getDescription()))
            .collect(Collectors.toList());
    }

    public ProjectDTO getProjectDTO(Long idProject) {
        Project project = projectDAO.getProjectById(idProject);
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
    }

    public ProjectDTO getProjectByTaskDTO(Long idTask) {
        Project project = projectDAO.getProjectByTask(idTask);
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
    }

    public List<TaskDTO> getProjectTasksDTO(Long idProject) {
        return projectDAO.getProjectTasks(idProject)
            .stream()
            .map(t -> new TaskDTO(t.getId(), t.getName(), t.getDescription()))
            .collect(Collectors.toList());
    }

    public PeriodDTO getPeriodDTO(Long idProject) {
        Project project = projectDAO.getProjectById(idProject);
        return new PeriodDTO(
            project.getStartDate().toString(),
            project.getEndDate().toString()
        );
    }

    public ProjectDTO createProject(ProjectDTO project) {
        Project newProject = new Project(null, project.getName());
        newProject.setDescription(project.getDescription());
        newProject = projectDAO.createProject(newProject);
        return new ProjectDTO(
            newProject.getId(),
            newProject.getName(),
            newProject.getDescription()
        );
    }

    public Boolean updateProject(ProjectDTO project) {
        Project newProject = new Project(null, project.getName());
        newProject.setDescription(project.getDescription());
        return projectDAO.updateProject(newProject);
    }

    public Boolean deleteProject(Long idProject) {
        return projectDAO.deleteProject(idProject);
    }

}
