package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import fr.devnr.jarialtekinapi.graphql.deserializer.ProjectDeserializer;
import fr.devnr.jarialtekinapi.graphql.deserializer.TaskDeserializer;
import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.graphql.serializer.ProjectSerializer;
import fr.devnr.jarialtekinapi.graphql.serializer.TaskSerializer;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.TaskService;


public class Mutation implements GraphQLMutationResolver {

    private TaskService taskService;
    private ProjectService projectService;


    public Mutation(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }


    public TaskDTO createTask(TaskDTO task) {
        return TaskSerializer.serialize(
            taskService.createTask(
                TaskDeserializer.deserialize(task)
            )
        );
    }

    public Boolean updateTask(TaskDTO task) {
        return taskService.updateTask( TaskDeserializer.deserialize(task) );
    }

    public Boolean deleteTask(Long idTask) {
        return taskService.deleteTask(idTask);
    }


    public ProjectDTO createProject(ProjectDTO project) {
        return ProjectSerializer.serialize(
            projectService.createProject(
                ProjectDeserializer.deserialize(project)
            )
        );
    }

    public Boolean updateProject(ProjectDTO project) {
        return projectService.updateProject( ProjectDeserializer.deserialize(project) );
    }

    public Boolean deleteProject(Long idProject) {
        return projectService.deleteProject(idProject);
    }

}
