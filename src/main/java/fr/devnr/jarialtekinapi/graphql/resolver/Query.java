package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.service.TaskService;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    private TaskService taskService;

    // Constructors
    // ============

    public Query(TaskService service) {
        this.taskService = service;
    }


    // ==============
    // Public Methods
    // ==============

    public List<TaskDto> allTasks() {
        return taskService.getAllTasksDTO();
    }

    public List<TaskDto> allTasksInPeriod(PeriodDto period) {
        return taskService.getTasksByPeriodDTO(period);
    }

    public TaskDto task(Long idTask) {
        return taskService.getTaskDTO(idTask);
    }

    // TODO : create method 'allProjects'
    // TODO : create method 'project'
    // TODO : create method 'projectTasks'
    // TODO : create method 'taskProject'

}
