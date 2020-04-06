package fr.devnr.jarialtekin.domain.ports.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskApiDTO {

    public String id;
    public String name;
    public String description;
    public LocalDateTime start;
    public LocalDateTime end;
    public PriorityApiDTO priority;
    public StatusApiDTO status;
    public TaskApiDTO parent;
    public List<TaskApiDTO> childs;
    public List<TaskApiDTO> dependencies;

}
