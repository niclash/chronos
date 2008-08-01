package org.qi4j.chronos.ui.workentry;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class WorkEntryDataProvider extends AbstractSortableDataProvider<WorkEntry>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasWorkEntries> hasWorkEntries;

    public WorkEntryDataProvider( IModel<? extends HasWorkEntries> hasWorkEntries )
    {
        this.hasWorkEntries = hasWorkEntries;
    }

    public IModel<WorkEntry> load( String id )
    {
        WorkEntry workEntry = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, WorkEntry.class );

        return new ChronosDetachableModel<WorkEntry>( workEntry );
    }

    public List<WorkEntry> dataList( int first, int count )
    {
        //TODO
        List<WorkEntry> workEntries = new ArrayList<WorkEntry>( hasWorkEntries.getObject().workEntries() );

        return workEntries.subList( first, first + count );
    }

    public int size()
    {
        return hasWorkEntries.getObject().workEntries().size();
    }
}
