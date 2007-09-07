package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.associations.HasLeadProjectAssignee;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.associations.HasPrimaryContactPerson;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.model.associations.HasProjectOwner;
import org.qi4j.chronos.model.associations.HasProjectStatus;
import org.qi4j.chronos.model.associations.HasProjectTimeRange;

public interface Project extends NameWithReference, HasProjectStatus, HasProjectTimeRange, HasPriceRateSchedules,
                                 HasLegalConditions, HasLeadProjectAssignee, HasProjectAssignees,
                                 HasProjectOwner, HasContactPersons, HasPrimaryContactPerson
{
    public final static int PROJECT_NAME_LEN = 120;

    public final static int PROJECT_FORMAL_REFERENCE_LEN = 250;
}
