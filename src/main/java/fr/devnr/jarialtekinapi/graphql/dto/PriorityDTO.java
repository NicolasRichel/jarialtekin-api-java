package fr.devnr.jarialtekinapi.graphql.dto;


public class PriorityDTO implements DataTransferObject {

    public Integer index;
    public String label;


    public PriorityDTO() {}

    public PriorityDTO(Integer index, String label) {
        this.index = index;
        this.label = label;
    }

}
