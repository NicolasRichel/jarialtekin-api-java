package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.model.Priority;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class PriorityServiceTest {

    @Test
    void GetStatusList() {
        // --{ ARRANGE }--
        PriorityService service = new PriorityService();
        // --{ ACT }--
        List<Priority> statuses = service.getPriorityList();
        // --{ ASSERT }--
        assertArrayEquals(Priority.values(), statuses.toArray());
    }

}
