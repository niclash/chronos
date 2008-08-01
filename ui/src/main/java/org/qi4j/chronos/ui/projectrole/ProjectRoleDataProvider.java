package org.qi4j.chronos.ui.projectrole;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.associations.HasProjectRoles;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class ProjectRoleDataProvider extends AbstractSortableDataProvider<ProjectRole>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasProjectRoles> hasProjectRoles;

    public ProjectRoleDataProvider( IModel<? extends HasProjectRoles> hasProjectRoles )
    {
        this.hasProjectRoles = hasProjectRoles;
    }

    public IModel<ProjectRole> load( String id )
    {
        ProjectRole projectRole = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, ProjectRole.class );

        return new ChronosDetachableModel<ProjectRole>( projectRole );
    }

    public List<ProjectRole> dataList( int first, int count )
    {
        //TODO
        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>( hasProjectRoles.getObject().projectRoles() );

        return projectRoles.subList( first, first + count );
    }

    public int size()
    {
        return hasProjectRoles.getObject().projectRoles().size();
    }
}
