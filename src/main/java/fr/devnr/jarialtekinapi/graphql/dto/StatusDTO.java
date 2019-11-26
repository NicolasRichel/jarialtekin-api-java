package fr.devnr.jarialtekinapi.graphql.dto;


public class StatusDTO implements DataTransferObject {

    public Integer index;
    public String label;


    public StatusDTO() {}

    public StatusDTO(Integer index, String label) {
        this.index = index;
        this.label = label;
    }

}
