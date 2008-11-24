package org.qi4j.chronos.domain.model.project;

import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.NameWithReference;
import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.chronos.domain.model.associations.HasContactPersons;
import org.qi4j.chronos.domain.model.associations.HasCustomer;
import org.qi4j.chronos.domain.model.associations.HasLegalConditions;
import org.qi4j.chronos.domain.model.associations.HasPriceRateSchedule;
import org.qi4j.chronos.domain.model.associations.HasPrimaryContactPerson;
import org.qi4j.chronos.domain.model.associations.HasProjectAssignees;
import org.qi4j.chronos.domain.model.associations.HasProjectTimeRange;
import org.qi4j.chronos.domain.model.associations.HasTasks;
import org.qi4j.chronos.domain.model.associations.HasWorkEntries;
import org.qi4j.entity.Identity;

public interface Project extends Entity<Project>, HasProjectTimeRange, HasPriceRateSchedule,
                                 HasLegalConditions, HasProjectAssignees, HasCustomer, HasContactPersons,
                                 HasPrimaryContactPerson, HasTasks, HasWorkEntries
{
    ProjectId projectId();

    ProjectStatus status();
}
