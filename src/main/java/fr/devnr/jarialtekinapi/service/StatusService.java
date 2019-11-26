package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Status;

import java.util.Arrays;
import java.util.List;


public class StatusService {

    public List<Status> getStatusList() {
        return Arrays.asList( Status.values() );
    }

}
