package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;


public class TaskSerializer {

    public static TaskDTO serialize(Task task) {
        if (task != null) {
            return new TaskDTO(task.getId(), task.getName(), task.getDescription());
        }
        return null;
    }

    public static TaskDTO serialize(TaskPlanning planning) {
        if (planning != null) {
            Task task = planning.getTask();
            return new TaskDTO(task.getId(), task.getName(), task.getDescription());
        }
        return null;
    }

}
