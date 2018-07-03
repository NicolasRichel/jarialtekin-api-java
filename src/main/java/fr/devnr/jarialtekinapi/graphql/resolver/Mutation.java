package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.TaskService;

public class Mutation implements GraphQLMutationResolver {

    private TaskService taskService;
    private ProjectService projectService;

    // Constructors
    // ============

    public Mutation(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }


    // ==============
    // Public Methods
    // ==============

    /** Task Mutations */
    /** ============== */
    public TaskDTO createTask(TaskDTO task) {
        return taskService.createTask(task);
    }

    public Boolean updateTask(TaskDTO task) {
        return taskService.updateTask(task);
    }

    public Boolean deleteTask(Long idTask) {
        return taskService.deleteTask(idTask);
    }
    /** ============== */

    /** Project Mutations */
    /** ================= */
    public ProjectDTO createProject(ProjectDTO project) {
        return projectService.createProject(project);
    }

    public Boolean updateProject(ProjectDTO project) {
        return projectService.updateProject(project);
    }

    public Boolean deleteProject(Long idProject) {
        return projectService.deleteProject(idProject);
    }
    /** ================= */

}
