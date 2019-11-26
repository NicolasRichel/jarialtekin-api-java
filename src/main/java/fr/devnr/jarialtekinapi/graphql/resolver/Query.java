package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import fr.devnr.jarialtekinapi.graphql.deserializer.PeriodDeserializer;
import fr.devnr.jarialtekinapi.graphql.dto.*;
import fr.devnr.jarialtekinapi.graphql.serializer.PrioritySerializer;
import fr.devnr.jarialtekinapi.graphql.serializer.ProjectSerializer;
import fr.devnr.jarialtekinapi.graphql.serializer.StatusSerializer;
import fr.devnr.jarialtekinapi.graphql.serializer.TaskSerializer;
import fr.devnr.jarialtekinapi.service.PriorityService;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.StatusService;
import fr.devnr.jarialtekinapi.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Query implements GraphQLQueryResolver {

    private PriorityService priorityService;
    private ProjectService projectService;
    private StatusService statusService;
    private TaskService taskService;


    public Query(
        PriorityService priorityService,
        ProjectService projectService,
        StatusService statusService,
        TaskService taskService
    ) {
        this.priorityService = priorityService;
        this.projectService = projectService;
        this.statusService = statusService;
        this.taskService = taskService;
    }


    public List<PriorityDTO> allPriorities() {
        return priorityService.getPriorityList().stream()
                .map(PrioritySerializer::serialize)
                .collect(Collectors.toList());
    }

    public List<StatusDTO> allStatuses() {
        return statusService.getStatusList().stream()
                .map(StatusSerializer::serialize)
                .collect(Collectors.toList());
    }


    public List<TaskDTO> allTasks() {
        return taskService.getAllTasks().stream()
                .map(TaskSerializer::serialize)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> allTasksInPeriod(PeriodDTO dto) {
        Map<String, LocalDateTime> period = PeriodDeserializer.deserialize(dto);
        return taskService.getTasksByPeriod(period.get("start"), period.get("end")).stream()
                .map(TaskSerializer::serialize)
                .collect(Collectors.toList());
    }

    public TaskDTO task(Long idTask) {
        return TaskSerializer.serialize( taskService.getTask(idTask) );
    }


    public List<ProjectDTO> allProjects() {
        return projectService.getAllProjects().stream()
                .map(ProjectSerializer::serialize)
                .collect(Collectors.toList());
    }

    public ProjectDTO project(Long idProject) {
        return ProjectSerializer.serialize( projectService.getProject(idProject) );
    }

    public ProjectDTO taskProject(Long idTask) {
        return ProjectSerializer.serialize( projectService.getProjectByTask(idTask) );
    }

    public List<TaskDTO> projectTasks(Long idProject) {
        return projectService.getProjectTasks(idProject).stream()
                .map(TaskSerializer::serialize)
                .collect(Collectors.toList());
    }

}
