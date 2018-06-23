package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
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
        List<TaskDto> result = service.getAllTasksDTO();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDto task = result.get(0);
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
        PeriodDto period = new PeriodDto("2018-02-03T09:00", "2018-02-05T12:30");
        List<TaskDto> result = service.getTasksByPeriodDTO(period);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskDto task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(taskPlanningDAO, times(1)).getTaskPlanningsByPeriod(eq(start), eq(end));
    }

    @Test
    void GetTasKDTO() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "T1");
        task.setDescription("Une tâche chiante");
        when(taskDAO.getTaskById(eq(1L))).thenReturn(task);
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        TaskDto result = service.getTaskDTO(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("T1", result.getName()),
            () -> assertEquals("Une tâche chiante", result.getDescription())
        );
        verify(taskDAO, times(1)).getTaskById(eq(1L));
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
        TaskService service = new TaskService(taskDAO, taskPlanningDAO);

        // --{ ACT }--
        PeriodDto result = service.getPeriodDTO(1L);

        // --{ ASSERT }--
        assertEquals("2000-01-01T13:00", result.getStart());
        assertEquals("2000-01-02T08:30", result.getEnd());
        verify(taskPlanningDAO, times(1)).getTaskPlanningByTask(eq(1L));
    }

}
