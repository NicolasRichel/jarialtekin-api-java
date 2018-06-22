package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
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

    public TaskDto task(Long idTask) {
        return taskService.getTaskDTO(idTask);
    }

}
