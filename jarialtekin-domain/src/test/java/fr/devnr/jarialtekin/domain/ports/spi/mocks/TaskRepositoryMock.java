package fr.devnr.jarialtekin.domain.ports.spi.mocks;

import fr.devnr.jarialtekin.domain.dataset.DomainDataSet;
import fr.devnr.jarialtekin.domain.ports.spi.TaskRepository;
import fr.devnr.jarialtekin.domain.ports.spi.dto.TaskSpiDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskRepositoryMock implements TaskRepository {

    @Override
    public List<TaskSpiDTO> getAll() {
        return Arrays.asList(
            DomainDataSet.task1(),
            DomainDataSet.task2(),
            DomainDataSet.task3()
        );
    }

    @Override
    public TaskSpiDTO getById(String id) {
        switch (id) {
            case "1": return DomainDataSet.task1();
            case "2": return DomainDataSet.task2();
            case "3": return DomainDataSet.task3();
            default: return null;
        }
    }

    @Override
    public TaskSpiDTO getParent(String id) {
        switch (id) {
            case "2": return DomainDataSet.task1();
            default: return null;
        }
    }

    @Override
    public List<TaskSpiDTO> getChilds(String id) {
        switch (id) {
            case "1": return Collections.singletonList(DomainDataSet.task2());
            default: return new ArrayList<>();
        }
    }

    @Override
    public List<TaskSpiDTO> getDependencies(String id) {
        switch (id) {
            case "3": return Collections.singletonList(DomainDataSet.task1());
            default: return null;
        }
    }

    @Override
    public TaskSpiDTO create(TaskSpiDTO task) {
        task.id = "1001";
        return task;
    }

    @Override
    public TaskSpiDTO update(TaskSpiDTO task) {
        return task;
    }

    @Override
    public Boolean delete(String id) {
        return true;
    }

}
