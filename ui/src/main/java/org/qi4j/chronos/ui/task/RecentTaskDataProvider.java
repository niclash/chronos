package org.qi4j.chronos.ui.task;

import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.associations.HasTasks;

public class RecentTaskDataProvider extends TaskDataProvider
{
    private static final long serialVersionUID = 1L;

    public RecentTaskDataProvider( IModel<? extends HasTasks> hasTasks )
    {
        super( hasTasks );
    }

    public int size()
    {
        //TODO
        return super.size();
    }

    public List<Task> dataList( int first, int count )
    {
        //TODO return recent tasks only
        return super.dataList( first, count );    //To change body of overridden methods use File | Settings | File Templates.
    }
}
