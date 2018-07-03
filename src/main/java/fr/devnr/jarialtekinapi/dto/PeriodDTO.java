package fr.devnr.jarialtekinapi.dto;

public class PeriodDTO {

    private String start;
    private String end;

    // Constructors
    // ============

    public PeriodDTO() {}

    public PeriodDTO(String start, String end) {
        this.start = start;
        this.end = end;
    }


    // Get & Set start
    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }

    // Get & Set end
    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }

}
