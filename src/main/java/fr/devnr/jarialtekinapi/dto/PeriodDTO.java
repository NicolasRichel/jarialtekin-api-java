package fr.devnr.jarialtekinapi.dto;

public class PeriodDTO {

    private String start;
    private String end;

    public PeriodDTO(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() { return start; }
    public String getEnd() { return end; }

}
