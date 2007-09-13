package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;

/**
 * Default mixin implementation for {@link org.qi4j.chronos.model.associations.HasProjectAssignees}
 */
public final class HasProjectAssigneesMixin implements HasProjectAssignees
{
    private final List<ProjectAssigneeEntityComposite> list;

    public HasProjectAssigneesMixin()
    {
        list = new ArrayList<ProjectAssigneeEntityComposite>();
    }

    public void addProjectAssignee( ProjectAssigneeEntityComposite assignee )
    {
        list.add( assignee );
    }

    public void removeProjectAssignee( ProjectAssigneeEntityComposite assignee )
    {
        list.remove( assignee );
    }

    public Iterator<ProjectAssigneeEntityComposite> projectAssigneeIterator()
    {
        return list.iterator();
    }

}
