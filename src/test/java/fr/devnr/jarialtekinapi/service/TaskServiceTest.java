package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        List<Task> result = service.getAllTasks();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        Task task = result.get(0);
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

    @Test
    void GetTasksByPeriod() {
        // --{ ARRANGE }--
        LocalDateTime start = LocalDateTime.of(2018, 2, 3, 9, 0);
        LocalDateTime end = LocalDateTime.of(2018, 2, 5, 12, 30);
        List<TaskPlanning> tasks = Arrays.asList(
            new TaskPlanning(
                new Task(1L, "T1"),
                LocalDateTime.of(2018, 2, 3, 15, 0),
                LocalDateTime.of(2018, 2, 3, 18, 0)
            ),
            new TaskPlanning(
                new Task(2L, "T2"),
                LocalDateTime.of(2018, 2, 4, 8, 30),
                LocalDateTime.of(2018, 2, 4, 12, 0)
            )
        );
        when(taskPlanningDAO.getTaskPlanningsByPeriod(eq(start), eq(end))).thenReturn(tasks);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        List<Task> result = service.getTasksByPeriod(start, end);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        Task task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(taskPlanningDAO, times(1)).getTaskPlanningsByPeriod(eq(start), eq(end));
    }

    @Test
    void GetTask() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "T1");
        task.setDescription("Une tâche chiante");
        when(taskDAO.getTaskById(eq(1L))).thenReturn(task);
        when(taskDAO.getTaskById(2L)).thenReturn(null);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        Task result1 = service.getTask(1L);
        Task result2 = service.getTask(2L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result1.getId()),
            () -> assertEquals("T1", result1.getName()),
            () -> assertEquals("Une tâche chiante", result1.getDescription())
        );
        verify(taskDAO, times(1)).getTaskById(eq(1L));
        assertNull(result2);
        verify(taskDAO, times(1)).getTaskById(eq(2L));
    }

    @Test
    void GetTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanning planning = new TaskPlanning(
            new Task(1L, "T1"),
            LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(13, 0)
            ),
            LocalDateTime.of(
                LocalDate.of(2000, 1, 2),
                LocalTime.of(8, 30)
            )
        );
        when(taskPlanningDAO.getTaskPlanningByTask(eq(1L))).thenReturn(planning);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        TaskPlanning result = service.getTaskPlanning(1L);

        // --{ ASSERT }--
        assertEquals(LocalDate.of(2000, 1, 1), result.getStartDate());
        assertEquals(LocalTime.of(13, 0), result.getStartTime());
        assertEquals(LocalDate.of(2000, 1, 2), result.getEndDate());
        assertEquals(LocalTime.of(8, 30), result.getEndTime());
        verify(taskPlanningDAO, times(1)).getTaskPlanningByTask(eq(1L));

    }

    @Test
    void CreateTask() {
        // --{ ARRANGE }--
        Task task = new Task(11L, "Task");
        task.setDescription("Description");
        when(taskDAO.createTask(any(Task.class))).thenReturn(task);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        Task result = service.createTask(task);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(11), result.getId()),
            () -> assertEquals("Task", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(taskDAO, times(1)).createTask(any(Task.class));
    }

    @Test
    void UpdateTask() {
        // --{ ARRANGE }--
        Task task = new Task(12L, "T12");
        when(taskDAO.getTaskById(eq(12L))).thenReturn(task);
        when(taskDAO.updateTask(any(Task.class))).thenReturn(Boolean.TRUE);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        Boolean result = service.updateTask(task);

        // --{ ASSERT }--
        assertTrue(result);
        verify(taskDAO, times(1)).getTaskById(eq(12L));
        verify(taskDAO, times(1)).updateTask(any(Task.class));
        verifyNoMoreInteractions(taskDAO);
    }

    @Test
    void DeleteTask() {
        // --{ ARRANGE }--
        when(taskDAO.deleteTask(eq(13L))).thenReturn(Boolean.TRUE);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        Boolean result = service.deleteTask(13L);

        // --{ ASSERT }--
        assertTrue(result);
        verify(taskDAO, times(1)).deleteTask(eq(13L));
    }

}
