package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import fr.devnr.jarialtekinapi.dto.*;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Query implements GraphQLQueryResolver {

    private TaskService taskService;
    private ProjectService projectService;

    // Constructors
    // ============

    public Query(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }


    // ==============
    // Public Methods
    // ==============

    public List<PriorityDTO> allPriorities() {
        return Arrays.stream(Priority.values())
            .map(p -> new PriorityDTO(p.getValue(), p.toString()))
            .collect(Collectors.toList());
    }

    public List<StatusDTO> allStatuses() {
        return Arrays.stream(Status.values())
            .map(s -> new StatusDTO(s.getValue(), s.toString()))
            .collect(Collectors.toList());
    }

    /** Task Queries */
    /** ------------ */
    public List<TaskDTO> allTasks() {
        return taskService.getAllTasksDTO();
    }

    public List<TaskDTO> allTasksInPeriod(PeriodDTO period) {
        return taskService.getTasksByPeriodDTO(period);
    }

    public TaskDTO task(Long idTask) {
        return taskService.getTaskDTO(idTask);
    }
    /** ------------ */

    /** Project Queries */
    /** --------------- */
    public List<ProjectDTO> allProjects() {
        return projectService.getAllProjectsDTO();
    }

    public ProjectDTO project(Long idProject) {
        return projectService.getProjectDTO(idProject);
    }

    public ProjectDTO taskProject(Long idTask) {
        return projectService.getProjectByTaskDTO(idTask);
    }

    public List<TaskDTO> projectTasks(Long idProject) {
        return projectService.getProjectTasksDTO(idProject);
    }
    /** --------------- */

}
