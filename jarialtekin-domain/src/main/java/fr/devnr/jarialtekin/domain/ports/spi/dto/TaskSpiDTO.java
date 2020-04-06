package fr.devnr.jarialtekin.domain.ports.spi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskSpiDTO {

    public String id;
    public String name;
    public String description;
    public LocalDateTime start;
    public LocalDateTime end;
    public PrioritySpiDTO priority;
    public StatusSpiDTO status;
    public TaskSpiDTO parent;
    public List<TaskSpiDTO> childs;
    public List<TaskSpiDTO> dependencies;

}
