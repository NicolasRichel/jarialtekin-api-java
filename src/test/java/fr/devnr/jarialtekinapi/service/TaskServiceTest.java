package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskDAO taskDAO = mock(TaskDAO.class);
    private TaskPlanningDAO taskPlanningDAO = mock(TaskPlanningDAO.class);


    @Test
    void GetAllTasks() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"), new Task(2L, "T2"), new Task(3L, "T3")
        );
        Task parent = new Task(101L, "P1");
        List<Task> subtasks = Arrays.asList(
            new Task(201L, "S1"), new Task(202L, "S2")
        );
        List<Task> dependencies = Arrays.asList(
            new Task(301L, "D1"), new Task(302L, "D2")
        );
        when(taskDAO.getAllTasks()).thenReturn(tasks);
        when(taskDAO.getParentTask(anyLong())).thenReturn(parent);
        when(taskDAO.getSubTasks(anyLong())).thenReturn(subtasks);
        when(taskDAO.getTaskDependencies(anyLong())).thenReturn(dependencies);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        List<TaskDto> result = service.getAllTasks();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDto task = result.get(0);
        assertAll("task",
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName()),
            () -> assertEquals(Long.valueOf(101), task.getParentTask().getId()),
            () -> assertEquals(2, task.getSubTasks().size()),
            () -> assertEquals(2, task.getDependencies().size())
        );
        verify(taskDAO, times(1)).getAllTasks();
        verify(taskDAO, times(3)).getParentTask(anyLong());
        verify(taskDAO, times(3)).getSubTasks(anyLong());
        verify(taskDAO, times(3)).getTaskDependencies(anyLong());
    }

}
