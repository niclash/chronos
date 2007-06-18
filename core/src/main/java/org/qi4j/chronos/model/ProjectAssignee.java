package org.qi4j.chronos.model;

import java.util.List;

public interface ProjectAssignee
{
    ProjectRole getProjectRole();

    List<WorkEntry> getWorkEntries();

    void addWorkEntry(WorkEntry workEntry);

    void removeWorkEntry(WorkEntry workEntry);
}
