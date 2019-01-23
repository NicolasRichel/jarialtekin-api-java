package fr.devnr.jarialtekinapi.dto;

public class StatusDTO {

    private Integer index;
    private String label;

    public StatusDTO() {}

    public StatusDTO(Integer index, String label) {
        this.index = index;
        this.label = label;
    }


    public Integer getIndex() { return index; }
    public String getLabel() { return label; }

}
