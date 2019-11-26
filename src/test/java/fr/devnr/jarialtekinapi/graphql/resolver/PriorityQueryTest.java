package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.service.PriorityService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class PriorityQueryTest {

    private PriorityService priorityService = mock(PriorityService.class);


    @Test
    void GetAllPriorities() {
        // --{ ARRANGE }--
        when(priorityService.getPriorityList()).thenReturn(Arrays.asList(Priority.values()));
        Query query = new Query(priorityService, null,null, null);
        // --{ ACT }--
        List<PriorityDTO> priorities = query.allPriorities();
        // --{ ASSERT }--
        assertEquals(3, priorities.size());
        assertAll(
            () -> {
                PriorityDTO p = priorities.get(0);
                assertEquals(Integer.valueOf(-1), p.index);
                assertEquals("LOW", p.label);
            },
            () -> {
                PriorityDTO p = priorities.get(1);
                assertEquals(Integer.valueOf(0), p.index);
                assertEquals("NORMAL", p.label);
            },
            () -> {
                PriorityDTO p = priorities.get(2);
                assertEquals(Integer.valueOf(1), p.index);
                assertEquals("HIGH", p.label);
            }
        );
    }

}
