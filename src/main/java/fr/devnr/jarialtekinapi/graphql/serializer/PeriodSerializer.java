package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.TaskPlanning;


public class PeriodSerializer {

    public static PeriodDTO serialize(TaskPlanning planning) {
        if (planning != null) {
            return new PeriodDTO(planning.getStart().toString(), planning.getEnd().toString());
        }
        return null;
    }

    public static PeriodDTO serialize(Project project) {
        if (project != null) {
            return new PeriodDTO(project.getStartDate().toString(), project.getEndDate().toString());
        }
        return null;
    }

}
