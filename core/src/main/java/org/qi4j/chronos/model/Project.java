package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.associations.HasCustomer;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.model.associations.HasPriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPrimaryContactPerson;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.model.associations.HasProjectStatus;
import org.qi4j.chronos.model.associations.HasProjectTimeRange;
import org.qi4j.chronos.model.associations.HasTasks;
import org.qi4j.chronos.model.associations.HasWorkEntries;

public interface Project extends NameWithReference, HasProjectStatus, HasProjectTimeRange, HasPriceRateSchedule,
                                 HasLegalConditions, HasProjectAssignees,
                                 HasCustomer, HasContactPersons, HasPrimaryContactPerson, HasTasks, HasWorkEntries
{
    public final static int PROJECT_NAME_LEN = 120;

    public final static int PROJECT_FORMAL_REFERENCE_LEN = 250;
}
