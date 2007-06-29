package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.WorkEntryComposite;

public interface HasWorkEntries extends Serializable

{
    void addWorkEntry( WorkEntryComposite workEntry );

    void removeWorkEntry( WorkEntryComposite workEntry );

    Iterator<WorkEntryComposite> workEntryIterator();
}
