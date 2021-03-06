package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.service.TaskService;

public class TaskResolver implements GraphQLResolver<TaskDTO> {

    private TaskService taskService;

    //  Constructors
    // ==============

    public TaskResolver(TaskService taskService) {
        this.taskService = taskService;
    }


    // ================
    //  Public Methods
    // ================

    public PeriodDTO planning(TaskDTO task) {
        return taskService.getPeriodDTO( task.getId() );
    }

}
