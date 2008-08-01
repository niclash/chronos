package org.qi4j.chronos.ui.projectassignee;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class ProjectAssigneeDataProvider extends AbstractSortableDataProvider<ProjectAssignee>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasProjectAssignees> hasProjectAssignees;

    public ProjectAssigneeDataProvider( IModel<? extends HasProjectAssignees> hasProjectAssignees )
    {
        this.hasProjectAssignees = hasProjectAssignees;
    }

    public IModel<ProjectAssignee> load( String id )
    {
        ProjectAssignee projectAssignee = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, ProjectAssignee.class );

        return new ChronosDetachableModel<ProjectAssignee>( projectAssignee );
    }

    public List<ProjectAssignee> dataList( int first, int count )
    {
        //TODO
        List<ProjectAssignee> projectAssignees = new ArrayList<ProjectAssignee>( hasProjectAssignees.getObject().projectAssignees() );

        return projectAssignees;
    }

    public int size()
    {
        return hasProjectAssignees.getObject().projectAssignees().size();
    }
}
