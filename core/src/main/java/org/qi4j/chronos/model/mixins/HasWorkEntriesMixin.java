package org.qi4j.chronos.model.mixins;

import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;

public class HasWorkEntriesMixin implements HasWorkEntries
{
    private List<WorkEntry> list;

    public void addWorkEntry( WorkEntry workEntry )
    {
        list.add( workEntry );
    }

    public void removeWorkEntry( WorkEntry workEntry )
    {
        list.remove( workEntry );
    }

    public Iterator<WorkEntry> workEntryIterator()
    {
        return list.iterator();
    }
}
