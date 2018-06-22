package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.service.TaskService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskQueryTest {

    private TaskService taskService = mock(TaskService.class);

    @Test
    void GetAllTasks() {
        // --{ ARRANGE }--
        List<TaskDto> tasks = Arrays.asList(
            new TaskDto(1L, "T1", "La tâche 1."),
            new TaskDto(2L, "T2", "Description."),
            new TaskDto(3L, "T3", "")
        );
        when(taskService.getAllTasksDTO()).thenReturn(tasks);
        Query query = new Query(taskService);

        // --{ ACT }--
        List<TaskDto> result = query.allTasks();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDto task = result.get(0);
        assertAll("task",
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName()),
            () -> assertEquals("La tâche 1.", task.getDescription())
        );
        verify(taskService, times(1)).getAllTasksDTO();
    }

}
