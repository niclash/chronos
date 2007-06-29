package org.qi4j.chronos.model.composites.association;

import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.ProjectPersistentComposite;

public interface HasProjects extends Serializable
{
    void addProject( ProjectPersistentComposite project );

    void removeProject( ProjectPersistentComposite project );

    Iterator<ProjectPersistentComposite> projectIterator();
}
