package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.ProjectComposite;

public interface HasProjects
{
    void addProject( ProjectComposite project );

    void removeProject( ProjectComposite project );

    List<ProjectComposite> getProjects();
}
