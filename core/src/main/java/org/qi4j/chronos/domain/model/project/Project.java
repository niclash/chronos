package org.qi4j.chronos.domain.model.project;

import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.associations.HasContactPersons;
import org.qi4j.chronos.domain.model.associations.HasCustomer;
import org.qi4j.chronos.domain.model.associations.HasPrimaryContactPerson;
import org.qi4j.chronos.domain.model.associations.HasProjectAssignees;
import org.qi4j.chronos.domain.model.associations.HasTasks;
import org.qi4j.chronos.domain.model.associations.HasWorkEntries;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.query.Query;

public interface Project extends Name, Entity<Project>, HasProjectAssignees, HasCustomer, HasContactPersons,
                                 HasPrimaryContactPerson, HasTasks, HasWorkEntries
{
    ProjectId projectId();

    ProjectDetail projectDetail();

    Query<PriceRateSchedule> priceRateSchedules();

    Query<LegalCondition> legalConditions();
}
