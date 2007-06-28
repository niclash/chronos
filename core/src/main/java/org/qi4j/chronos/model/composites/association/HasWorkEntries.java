package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.WorkEntryComposite;

public interface HasWorkEntries
{
    void addWorkEntry( WorkEntryComposite workEntry );

    void removeWorkEntry( WorkEntryComposite workEntry );

    Iterator<WorkEntryComposite> workEntryIterator();
}
