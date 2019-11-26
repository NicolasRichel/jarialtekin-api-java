package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;
import fr.devnr.jarialtekinapi.service.TaskService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TaskResolverTest {

    private TaskService taskService = mock(TaskService.class);


    @Test
    void GetPlanning() {
        // --{ ARRANGE }--
        TaskPlanning planning = new TaskPlanning(
            new Task(1L, "T1"),
            LocalDateTime.of(
                LocalDate.of(2002, 7, 14),
                LocalTime.of(13, 0)
            ),
            LocalDateTime.of(
                LocalDate.of(2002, 8, 15),
                LocalTime.of(8, 30)
            )
        );
        when(taskService.getTaskPlanning(eq(1L))).thenReturn(planning);
        TaskResolver taskResolver = new TaskResolver(taskService);

        // --{ ACT }--
        TaskDTO task = new TaskDTO(1L, "Task", "");
        PeriodDTO result = taskResolver.planning(task);

        // --{ ASSERT }--
        assertEquals("2002-07-14T13:00", result.start);
        assertEquals("2002-08-15T08:30", result.end);
        verify(taskService, times(1)).getTaskPlanning(eq(1L));
    }

}
