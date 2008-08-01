package org.qi4j.chronos.ui.project;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class ProjectDataProvider extends AbstractSortableDataProvider<Project>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasProjects> hasProjects;

    public ProjectDataProvider( IModel<? extends HasProjects> hasProjects )
    {
        this.hasProjects = hasProjects;
    }

    public IModel<Project> load( String id )
    {
        Project project = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, Project.class );

        return new ChronosDetachableModel<Project>( project );
    }

    public List<Project> dataList( int first, int count )
    {
        //TODO
        List<Project> projects = new ArrayList<Project>( hasProjects.getObject().projects() );

        return projects.subList( first, first + count );
    }

    public int size()
    {
        return hasProjects.getObject().projects().size();
    }
}
