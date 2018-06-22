package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.service.TaskService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskResolverTest {

    private TaskService taskService = mock(TaskService.class);


    @Test
    void GetPlanning() {
        // --{ ARRANGE }--
        TaskDto task = new TaskDto(1L, "Task", "");
        PeriodDto period = new PeriodDto("2018-01-01T13:00", "2018-01-02T08:30");
        when(taskService.getPeriodDTO(eq(1L))).thenReturn(period);
        TaskResolver taskResolver = new TaskResolver(taskService);

        // --{ ACT }--
        PeriodDto result = taskResolver.planning(task);

        // --{ ASSERT }--
        assertEquals("2018-01-01T13:00", result.getStart());
        assertEquals("2018-01-02T08:30", result.getEnd());
        verify(taskService, times(1)).getPeriodDTO(eq(1L));
    }

}
