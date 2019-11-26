package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.model.Priority;

import java.util.Arrays;
import java.util.List;


public class PriorityService {

    public List<Priority> getPriorityList() {
        return Arrays.asList( Priority.values() );
    }

}
