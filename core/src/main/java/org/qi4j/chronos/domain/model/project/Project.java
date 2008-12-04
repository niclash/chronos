package org.qi4j.chronos.domain.model.project;

import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.name.MutableReferenceName;
import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.common.task.TaskPriority;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.task.ProjectTask;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;
import org.qi4j.query.Query;

public interface Project extends Name, MutableReferenceName, Entity<Project>
{
    ProjectId projectId();

    ProjectStatus status();

    Period estimateTime();

    Customer customer();

    Query<ContactPerson> contactPersons();
    ContactPerson primaryContactPerson();

    Query<ProjectAssignee> projectAssignees();
    ProjectAssignee addProjectAssignee( ProjectRole role );
    void removeProjectAssignee( ProjectAssignee assignee );
    ProjectAssignee projectLeader();

    PriceRateSchedule priceRateSchedule();
    void updatePriceRateSchedule( PriceRateSchedule newSchedule );

    Query<ProjectTask> tasks();
    void createTask( String name, String description, TaskPriority priority, User reportedBy, User assignedTo );

    // TODO
    Query<LegalCondition> legalConditions();
}
