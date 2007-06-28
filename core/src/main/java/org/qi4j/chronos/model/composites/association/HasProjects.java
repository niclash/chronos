package org.qi4j.chronos.model.composites.association;

import java.util.Iterator;
import org.qi4j.chronos.model.composites.ProjectPersistentComposite;

public interface HasProjects
{
    void addProject( ProjectPersistentComposite project );

    void removeProject( ProjectPersistentComposite project );

    Iterator<ProjectPersistentComposite> projectIterator();
}
