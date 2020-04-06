package fr.devnr.jarialtekin.domain.ports.spi;

import fr.devnr.jarialtekin.domain.ports.spi.dto.TaskSpiDTO;

import java.util.List;

public interface TaskRepository {

    List<TaskSpiDTO> getAll();

    TaskSpiDTO getById(String id);

    TaskSpiDTO getParent(String id);

    List<TaskSpiDTO> getChilds(String id);

    List<TaskSpiDTO> getDependencies(String id);

    TaskSpiDTO create(TaskSpiDTO task);

    TaskSpiDTO update(TaskSpiDTO task);

    Boolean delete(String id);

}
