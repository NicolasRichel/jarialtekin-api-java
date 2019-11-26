package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.service.TaskService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TaskQueryTest {

    private TaskService taskService = mock(TaskService.class);


    @Test
    void GetAllTasks() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"),
            new Task(2L, "T2"),
            new Task(3L, "T3")
        );
        when(taskService.getAllTasks()).thenReturn(tasks);
        Query query = new Query(null, null, null, taskService);

        // --{ ACT }--
        List<TaskDTO> result = query.allTasks();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.id),
            () -> assertEquals("T1", task.name)
        );
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void GetAllTasksInPeriod() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "X"),
            new Task(2L, "Y")
        );
        when(taskService.getTasksByPeriod(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(tasks);
        Query query = new Query(null, null, null, taskService);

        // --{ ACT }--
        PeriodDTO period = new PeriodDTO("2018-06-21T10:00", "2018-07-18T12:00");
        List<TaskDTO> result = query.allTasksInPeriod(period);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.id),
            () -> assertEquals("X", task.name)
        );
        verify(taskService, times(1)).getTasksByPeriod(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void GetTask() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "T1");
        when(taskService.getTask(eq(1L))).thenReturn(task);
        Query query = new Query(null, null, null, taskService);

        // --{ ACT }--
        TaskDTO result = query.task(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.id),
            () -> assertEquals("T1", result.name)
        );
        verify(taskService, times(1)).getTask(eq(1L));
    }

}
