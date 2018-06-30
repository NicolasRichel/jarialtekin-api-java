package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
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

    public TaskDTO createTask(TaskDTO task) {
        return taskService.createTask(task);
    }

    public Boolean updateTask(TaskDTO task) {
        return taskService.updateTask(task);
    }

    public Boolean deleteTask(Long idTask) {
        return taskService.deleteTask(idTask);
    }

    // TODO : create method 'createProject'
    // TODO : create method 'updateProject'
    // TODO : create method 'deleteProject'

}
