package fr.devnr.jarialtekinapi.graphql.dto;


public class StatusDTO implements DataTransferObject {

    public Integer id;
    public String label;


    public StatusDTO() {}

    public StatusDTO(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

}
