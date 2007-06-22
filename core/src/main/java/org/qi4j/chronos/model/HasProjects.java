package org.qi4j.chronos.model;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.ProjectComposite;

public interface HasProjects
{
    void addProject( ProjectComposite project );

    void removeProject( ProjectComposite project );

    Iterator<ProjectComposite> projectIterator();
}
