package fr.devnr.jarialtekinapi.graphql.dto;


public class TaskDTO implements DataTransferObject {

    public Long id;
    public String name;
    public String description;


    public TaskDTO() {}

    public TaskDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
