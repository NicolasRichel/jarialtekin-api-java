package fr.devnr.jarialtekinapi.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void AddOneSubTask() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        Task subTask = new Task(2L, "SubTask");

        // --{ ACT }--
        task.addSubTask(subTask);

        // --{ ASSERT }--
        assertEquals(1, task.getSubTasks().size());
        assertEquals(Long.valueOf(2), task.getSubTasks().get(0).getId());
        assertEquals("SubTask", task.getSubTasks().get(0).getName());
    }

    @Test
    void AddMultipleSubTasks() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        List<Task> subTasks = Arrays.asList(
            new Task(2L, "SubTask 1"),
            new Task(3L, "SubTask 2")
        );

        // --{ ACT }--
        task.addSubTasks(subTasks);

        // --{ ASSERT }--
        assertEquals(2, task.getSubTasks().size());
        assertEquals(Long.valueOf(3), task.getSubTasks().get(1).getId());
        assertEquals("SubTask 2", task.getSubTasks().get(1).getName());
    }

    @Test
    void RemoveOneSubTask() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        task.addSubTasks(Arrays.asList(
            new Task(2L, "SubTask 1"),
            new Task(3L, "SubTask 2")
        ));

        // --{ ACT }--
        task.removeSubTask(new Task(2L, "SubTask to remove"));

        // --{ ASSERT }--
        assertEquals(1, task.getSubTasks().size());
        assertEquals(Long.valueOf(3), task.getSubTasks().get(0).getId());
        assertEquals("SubTask 2", task.getSubTasks().get(0).getName());
    }

    @Test
    void RemoveMultipleSubTasks() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        task.addSubTasks(Arrays.asList(
            new Task(2L, "SubTask 1"),
            new Task(3L, "SubTask 2")
        ));

        // --{ ACT }--
        task.removeSubTasks(Arrays.asList(
            new Task(2L, "Task A"),
            new Task(3L, "Task B")
        ));

        // --{ ASSERT }--
        assertEquals(0, task.getSubTasks().size());
    }

    @Test
    void AddOneDependency() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        Task dependency = new Task(2L, "Dependency");

        // --{ ACT }--
        task.addDependency(dependency);

        // --{ ASSERT }--
        assertEquals(1, task.getDependencies().size());
        assertEquals(Long.valueOf(2), task.getDependencies().get(0).getId());
        assertEquals("Dependency", task.getDependencies().get(0).getName());
    }

    @Test
    void AddMultipleDependencies() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        List<Task> dependencies = Arrays.asList(
            new Task(2L, "Dependency 1"),
            new Task(3L, "Dependency 2")
        );

        // --{ ACT }--
        task.addDependencies(dependencies);

        // --{ ASSERT }--
        assertEquals(2, task.getDependencies().size());
        assertEquals(Long.valueOf(3), task.getDependencies().get(1).getId());
        assertEquals("Dependency 2", task.getDependencies().get(1).getName());
    }

    @Test
    void RemoveOneDependency() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        task.addDependencies(Arrays.asList(
            new Task(2L, "Dependency 1"),
            new Task(3L, "Dependency 2")
        ));

        // --{ ACT }--
        task.removeDependency(new Task(2L, "Dependency to remove"));

        // --{ ASSERT }--
        assertEquals(1, task.getDependencies().size());
        assertEquals(Long.valueOf(3), task.getDependencies().get(0).getId());
        assertEquals("Dependency 2", task.getDependencies().get(0).getName());
    }

    @Test
    void RemoveMultipleDependencies() {
        // --{ ARRANGE }--
        Task task = new Task(1L, "Task");
        task.addDependencies(Arrays.asList(
            new Task(2L, "Dependency 1"),
            new Task(3L, "Dependency 2")
        ));

        // --{ ACT }--
        task.removeDependencies(Arrays.asList(
            new Task(2L, "Task A"),
            new Task(3L, "Task B")
        ));

        // --{ ASSERT }--
        assertEquals(0, task.getDependencies().size());
    }

}
