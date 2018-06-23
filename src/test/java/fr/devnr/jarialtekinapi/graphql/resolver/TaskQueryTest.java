package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
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
        List<TaskDto> tasks = Arrays.asList(
            new TaskDto(1L, "X", "abcd"),
            new TaskDto(2L, "Y", "1234")
        );
        when(taskService.getTasksByPeriodDTO(any())).thenReturn(tasks);
        Query query = new Query(taskService);

        // --{ ACT }--
        PeriodDto period = new PeriodDto("2018-06-21T10:00", "2018-07-18T12:00");
        List<TaskDto> result = query.allTasksInPeriod(period);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskDto task = result.get(0);
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
        TaskDto task = new TaskDto(1L, "T1", "Description");
        when(taskService.getTaskDTO(eq(1L))).thenReturn(task);
        Query query = new Query(taskService);

        // --{ ACT }--
        TaskDto result = query.task(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("T1", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(taskService, times(1)).getTaskDTO(eq(1L));
    }

}
