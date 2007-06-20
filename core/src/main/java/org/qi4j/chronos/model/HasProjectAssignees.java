package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.ProjectAssigneeComposite;
import java.util.List;

public interface HasProjectAssignees
{
    ProjectAssigneeComposite getProjectLead();

    void setProjectLead(ProjectAssigneeComposite lead);

    List<ProjectAssigneeComposite> getProjectAssignees();

    void addProjectAssignee(ProjectAssigneeComposite assignee);

    void removeProjectAssignee(ProjectAssigneeComposite assignee);     
}
