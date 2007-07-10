package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.model.Project;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class HasProjectsMixin implements HasProjects
{
    private List<Project> list;

    public HasProjectsMixin()
    {
        list = new ArrayList<Project>();
    }

    public void addProject( Project project )
    {
        list.add( project );
    }

    public void removeProject( Project project )
    {
        list.remove( project );
    }

    public Iterator<Project> projectIterator()
    {
        return list.iterator();
    }
}
