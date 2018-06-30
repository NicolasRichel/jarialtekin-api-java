package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
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
        List<TaskDTO> tasks = Arrays.asList(
            new TaskDTO(1L, "T1", "La tâche 1."),
            new TaskDTO(2L, "T2", "Description."),
            new TaskDTO(3L, "T3", "")
        );
        when(taskService.getAllTasksDTO()).thenReturn(tasks);
        Query query = new Query(taskService, null);

        // --{ ACT }--
        List<TaskDTO> result = query.allTasks();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName()),
            () -> assertEquals("La tâche 1.", task.getDescription())
        );
        verify(taskService, times(1)).getAllTasksDTO();
    }

    @Test
    void GetAllTasksInPeriod() {
        // --{ ARRANGE }--
        List<TaskDTO> tasks = Arrays.asList(
            new TaskDTO(1L, "X", "abcd"),
            new TaskDTO(2L, "Y", "1234")
        );
        when(taskService.getTasksByPeriodDTO(any())).thenReturn(tasks);
        Query query = new Query(taskService, null);

        // --{ ACT }--
        PeriodDTO period = new PeriodDTO("2018-06-21T10:00", "2018-07-18T12:00");
        List<TaskDTO> result = query.allTasksInPeriod(period);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("X", task.getName()),
            () -> assertEquals("abcd", task.getDescription())
        );
        verify(taskService, times(1)).getTasksByPeriodDTO(any());
    }

    @Test
    void GetTask() {
        // --{ ARRANGE }--
        TaskDTO task = new TaskDTO(1L, "T1", "Description");
        when(taskService.getTaskDTO(eq(1L))).thenReturn(task);
        Query query = new Query(taskService, null);

        // --{ ACT }--
        TaskDTO result = query.task(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("T1", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(taskService, times(1)).getTaskDTO(eq(1L));
    }

}
