package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.service.TaskService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskMutationTest {

    private TaskService taskService = mock(TaskService.class);


    @Test
    void CreateTask() {
        // --{ ARRANGE }--
        TaskDTO task = new TaskDTO(11L, "Task", "Description");
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);
        Mutation mutation = new Mutation(taskService, null);

        // --{ ACT }--
        TaskDTO input = new TaskDTO(null, "Task", "Description");
        TaskDTO result = mutation.createTask(input);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(11), result.getId()),
            () -> assertEquals("Task", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(taskService, times(1)).createTask(any(TaskDTO.class));
    }

    @Test
    void UpdateTask() {
        // --{ ARRANGE }--
        when(taskService.updateTask(any(TaskDTO.class))).thenReturn(Boolean.TRUE);
        Mutation mutation = new Mutation(taskService, null);

        // --{ ACT }--
        TaskDTO input = new TaskDTO(12L, "T12", "");
        Boolean result = mutation.updateTask(input);

        // --{ ASSERT }--
        assertTrue(result);
        verify(taskService, times(1)).updateTask(any(TaskDTO.class));
    }

    @Test
    void DeleteTask() {
        // --{ ARRANGE }--
        when(taskService.deleteTask(eq(13L))).thenReturn(Boolean.TRUE);
        Mutation mutation = new Mutation(taskService, null);

        // --{ ACT }--
        Boolean result = mutation.deleteTask(13L);

        // --{ ASSERT }--
        assertTrue(result);
        verify(taskService, times(1)).deleteTask(eq(13L));
    }

}
