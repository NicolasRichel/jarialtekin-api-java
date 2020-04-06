package fr.devnr.jarialtekin.domain.dataset;

import fr.devnr.jarialtekin.domain.ports.spi.dto.PrioritySpiDTO;
import fr.devnr.jarialtekin.domain.ports.spi.dto.StatusSpiDTO;
import fr.devnr.jarialtekin.domain.ports.spi.dto.TaskSpiDTO;

public final class DomainDataSet {

    private DomainDataSet() {}

    public static StatusSpiDTO status(String id, Integer index, String name) {
        StatusSpiDTO status = new StatusSpiDTO();
        status.id = id;
        status.index = index;
        status.name = name;
        return status;
    }

    public static PrioritySpiDTO priority(String id, Integer index, String name) {
        PrioritySpiDTO priority = new PrioritySpiDTO();
        priority.id = id;
        priority.index = index;
        priority.name = name;
        return priority;
    }

    public static TaskSpiDTO task1() {
        TaskSpiDTO task = new TaskSpiDTO();
        task.id = "1";
        task.name = "T1";
        task.description = "The first task.";
        task.status = status("1", 0, "TODO");
        task.priority = priority("1", 0, "Normal");
        return task;
    }

    public static TaskSpiDTO task2() {
        TaskSpiDTO task = new TaskSpiDTO();
        task.id = "2";
        task.name = "T2";
        task.description = "The second task.";
        task.status = status("1", 0, "TODO");
        task.priority = priority("2", 1, "High");
        return task;
    }

    public static TaskSpiDTO task3() {
        TaskSpiDTO task = new TaskSpiDTO();
        task.id = "3";
        task.name = "T3";
        task.description = "The third task.";
        task.status = status("2", 1, "DOING");
        task.priority = priority("1", 0, "Normal");
        return task;
    }

}
