package fr.devnr.jarialtekinapi.dto;

public class PriorityDTO {

    private Integer index;
    private String label;

    public PriorityDTO() {}

    public PriorityDTO(Integer index, String label) {
        this.index = index;
        this.label = label;
    }


    public Integer getIndex() { return index; }
    public String getLabel() { return label; }
}
