package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.WorkEntryComposite;

public interface HasWorkEntries
{
    void addWorkEntry( WorkEntryComposite workEntry );

    void removeWorkEntry( WorkEntryComposite workEntry );

    List<WorkEntryComposite> getWorkEntries();
}
