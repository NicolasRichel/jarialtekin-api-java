package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.dto.StatusDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTest {

    @Test
    void GetAllPriorities() {
        // --{ ARRANGE }--
        Query query = new Query(null, null);
        // --{ ACT }--
        List<PriorityDTO> priorities = query.allPriorities();
        // --{ ASSERT }--
        assertEquals(3, priorities.size());
        assertAll(
            () -> {
                PriorityDTO p = priorities.get(0);
                assertEquals(Integer.valueOf(-1), p.getIndex());
                assertEquals("LOW", p.getLabel());
            },
            () -> {
                PriorityDTO p = priorities.get(1);
                assertEquals(Integer.valueOf(0), p.getIndex());
                assertEquals("NORMAL", p.getLabel());
            },
            () -> {
                PriorityDTO p = priorities.get(2);
                assertEquals(Integer.valueOf(1), p.getIndex());
                assertEquals("HIGH", p.getLabel());
            }
        );
    }

    @Test
    void GetAllStatuses() {
        // --{ ARRANGE }--
        Query query = new Query(null, null);
        // --{ ACT }--
        List<StatusDTO> statuses = query.allStatuses();
        // --{ ASSERT }--
        assertEquals(4, statuses.size());
        assertAll(
            () -> {
                StatusDTO s = statuses.get(0);
                assertEquals(Integer.valueOf(-1), s.getIndex());
                assertEquals("NONE", s.getLabel());
            },
            () -> {
                StatusDTO s = statuses.get(1);
                assertEquals(Integer.valueOf(0), s.getIndex());
                assertEquals("TODO", s.getLabel());
            },
            () -> {
                StatusDTO s = statuses.get(2);
                assertEquals(Integer.valueOf(1), s.getIndex());
                assertEquals("DOING", s.getLabel());
            },
            () -> {
                StatusDTO s = statuses.get(3);
                assertEquals(Integer.valueOf(2), s.getIndex());
                assertEquals("DONE", s.getLabel());
            }
        );
    }

}
