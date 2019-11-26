package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.graphql.serializer.PeriodSerializer;
import fr.devnr.jarialtekinapi.service.TaskService;


public class TaskResolver implements GraphQLResolver<TaskDTO> {

    private TaskService taskService;


    public TaskResolver(TaskService taskService) {
        this.taskService = taskService;
    }


    public PeriodDTO planning(TaskDTO task) {
        return PeriodSerializer.serialize( taskService.getTaskPlanning( task.id ) );
    }

}
