package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.StatusDTO;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.service.StatusService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class StatusQueryTest {

    private StatusService statusService = mock(StatusService.class);


    @Test
    void GetAllStatuses() {
        // --{ ARRANGE }--
        when(statusService.getStatusList()).thenReturn(Arrays.asList(Status.values()));
        Query query = new Query(null, null, statusService, null);
        // --{ ACT }--
        List<StatusDTO> statuses = query.allStatuses();
        // --{ ASSERT }--
        assertEquals(4, statuses.size());
        assertAll(
            () -> {
                StatusDTO s = statuses.get(0);
                assertEquals(Integer.valueOf(-1), s.index);
                assertEquals("NONE", s.label);
            },
            () -> {
                StatusDTO s = statuses.get(1);
                assertEquals(Integer.valueOf(0), s.index);
                assertEquals("TODO", s.label);
            },
            () -> {
                StatusDTO s = statuses.get(2);
                assertEquals(Integer.valueOf(1), s.index);
                assertEquals("DOING", s.label);
            },
            () -> {
                StatusDTO s = statuses.get(3);
                assertEquals(Integer.valueOf(2), s.index);
                assertEquals("DONE", s.label);
            }
        );
    }

}
