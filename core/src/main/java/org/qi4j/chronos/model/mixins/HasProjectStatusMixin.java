package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.associations.HasProjectStatus;

public class HasProjectStatusMixin implements HasProjectStatus
{
    private ProjectStatus projectStatus;

    public void setProjectStatus( ProjectStatus projectStatus )
    {
        this.projectStatus = projectStatus;
    }

    public ProjectStatus getProjectStatus()
    {
        return projectStatus;
    }
}
