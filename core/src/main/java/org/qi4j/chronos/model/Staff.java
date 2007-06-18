package org.qi4j.chronos.model;

import java.util.List;

public interface Staff extends User
{
    double getSalary();

    double getBasicRate();

    TimeRange getTimeRange();

    List<ProjectAssignee> getProjects();

    void addProject(ProjectAssignee project);

    void removeProject(ProjectAssignee project);
}
