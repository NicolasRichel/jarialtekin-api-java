package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.model.Status;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StatusServiceTest {

    @Test
    void GetStatusList() {
        // --{ ARRANGE }--
        StatusService service = new StatusService();
        // --{ ACT }--
        List<Status> statuses = service.getStatusList();
        // --{ ASSERT }--
        assertArrayEquals(Status.values(), statuses.toArray());
    }

}
