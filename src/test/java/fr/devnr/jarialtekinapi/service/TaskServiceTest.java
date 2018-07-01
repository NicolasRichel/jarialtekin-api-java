package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
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
    void GetAllTasksModel() {
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
        List<Task> result = service.getAllTasksModel();

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
    void GetAllTasksDTO() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"), new Task(2L, "T2"), new Task(3L, "T3")
        );
        when(taskDAO.getAllTasks()).thenReturn(tasks);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        List<TaskDTO> result = service.getAllTasksDTO();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll("task",
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(taskDAO, times(1)).getAllTasks();
    }

    @Test
    void GetTasksByPeriodDTO() {
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
        PeriodDTO period = new PeriodDTO("2018-02-03T09:00", "2018-02-05T12:30");
        List<TaskDTO> result = service.getTasksByPeriodDTO(period);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(taskPlanningDAO, times(1)).getTaskPlanningsByPeriod(eq(start), eq(end));
    }

    @Test
    void GetTaskDTO() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "T1");
        task.setDescription("Une tâche chiante");
        when(taskDAO.getTaskById(eq(1L))).thenReturn(task);
        when(taskDAO.getTaskById(2L)).thenReturn(null);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        TaskDTO result1 = service.getTaskDTO(1L);
        TaskDTO result2 = service.getTaskDTO(2L);

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
    void GetPeriodDTO() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "T1");
        TaskPlanning planning = new TaskPlanning(
            task,
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
        when(taskPlanningDAO.getTaskPlanningByTask(eq(2L))).thenReturn(null);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        PeriodDTO result1 = service.getPeriodDTO(1L);
        PeriodDTO result2 = service.getPeriodDTO(2L);

        // --{ ASSERT }--
        assertEquals("2000-01-01T13:00", result1.getStart());
        assertEquals("2000-01-02T08:30", result1.getEnd());
        verify(taskPlanningDAO, times(1)).getTaskPlanningByTask(eq(1L));
        assertNull(result2);
        verify(taskPlanningDAO, times(1)).getTaskPlanningByTask(eq(2L));

    }

    @Test
    void CreateTask() {
        // --{ ARRANGE }--
        Task task = new Task(11L, "Task");
        task.setDescription("Description");
        when(taskDAO.createTask(any(Task.class))).thenReturn(task);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        TaskDTO input = new TaskDTO(null, "Task", "Description");
        TaskDTO result = service.createTask(input);

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
        when(taskDAO.updateTask(any(Task.class))).thenReturn(Boolean.TRUE);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        TaskDTO input = new TaskDTO(12L, "T12", "Description");
        Boolean result = service.updateTask(input);

        // --{ ASSERT }--
        assertTrue(result);
        verify(taskDAO, times(1)).updateTask(any(Task.class));
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
