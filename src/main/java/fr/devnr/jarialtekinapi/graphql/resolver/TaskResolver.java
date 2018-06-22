package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.service.TaskService;

public class TaskResolver implements GraphQLResolver<TaskDto> {

    private TaskService taskService;

    //  Constructors
    // ==============

    public TaskResolver(TaskService taskService) {
        this.taskService = taskService;
    }


    // ================
    //  Public Methods
    // ================

    public PeriodDto planning(TaskDto task) {
        return taskService.getPeriodDTO( task.getId() );
    }

}
