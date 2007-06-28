package org.qi4j.chronos.model.composites.association;

import org.qi4j.chronos.model.composites.ProjectAssigneeComposite;
import java.util.List;
import java.util.Iterator;

public interface HasProjectAssignees
{
    ProjectAssigneeComposite getProjectLead();

    void setProjectLead(ProjectAssigneeComposite lead);

    Iterator<ProjectAssigneeComposite> projectAssigneeIterator();

    void addProjectAssignee(ProjectAssigneeComposite assignee);

    void removeProjectAssignee(ProjectAssigneeComposite assignee);     
}
