package fr.devnr.jarialtekinapi.graphql.dto;


public class ProjectDTO implements DataTransferObject {

    public Long id;
    public String name;
    public String description;


    public ProjectDTO() {}

    public ProjectDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
