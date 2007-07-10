package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.associations.HasProjectAssignees;

public class HasProjectAssigneesMixin implements HasProjectAssignees
{
    private List<ProjectAssignee> list;

    public HasProjectAssigneesMixin()
    {
        list = new ArrayList<ProjectAssignee>();
    }

    public void addProjectAssignee( ProjectAssignee assignee )
    {
        list.add( assignee );
    }

    public void removeProjectAssignee( ProjectAssignee assignee )
    {
        list.remove( assignee );
    }

    public Iterator<ProjectAssignee> projectAssigneeIterator()
    {
        return list.iterator();
    }

}
