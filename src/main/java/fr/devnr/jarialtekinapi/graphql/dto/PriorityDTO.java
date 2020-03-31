package fr.devnr.jarialtekinapi.graphql.dto;


public class PriorityDTO implements DataTransferObject {

    public Integer id;
    public String label;


    public PriorityDTO() {}

    public PriorityDTO(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

}
