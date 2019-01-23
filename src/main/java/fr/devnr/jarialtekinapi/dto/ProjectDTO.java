package fr.devnr.jarialtekinapi.dto;

public class ProjectDTO {

    private Long id;
    private String name;
    private String description;

    // Contructors
    // ===========

    public ProjectDTO() {}

    public ProjectDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    // Get & Set id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Get & Set name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Get & Set description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
