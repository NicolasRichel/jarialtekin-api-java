package fr.devnr.jarialtekinapi.graphql.dto;


public class PeriodDTO implements DataTransferObject {

    public String start;
    public String end;


    public PeriodDTO() {}

    public PeriodDTO(String start, String end) {
        this.start = start;
        this.end = end;
    }

}
