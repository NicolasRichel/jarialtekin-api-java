package fr.devnr.jarialtekinapi.dto;

public class PeriodDto {

    private String start;
    private String end;

    public PeriodDto(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() { return start; }
    public String getEnd() { return end; }

}
