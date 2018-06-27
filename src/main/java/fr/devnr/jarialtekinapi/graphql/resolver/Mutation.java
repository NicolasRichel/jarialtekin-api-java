package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.service.TaskService;

public class Mutation implements GraphQLMutationResolver {

    private TaskService taskService;

    // Constructors
    // ============

    public Mutation(TaskService service) {
        this.taskService = service;
    }


    // ==============
    // Public Methods
    // ==============

    // TODO : write method 'createTask'
    public TaskDto createTask(TaskDto task) {
        return null;
    }

    // TODO : create method 'updateTask'
    // TODO : create method 'deleteTask'
    // TODO : create method 'createProject'
    // TODO : create method 'updateProject'
    // TODO : create method 'deleteProject'

}
