package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.association.HasProjectTimeRange;
import org.qi4j.chronos.model.composites.association.HasProjectAssignees;
import org.qi4j.chronos.model.composites.association.HasLegalConditions;
import org.qi4j.chronos.model.composites.association.HasProjectContacts;
import org.qi4j.chronos.model.composites.association.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.association.HasProjectStatus;

public interface Project extends NameWithReference, HasProjectTimeRange, HasProjectAssignees, HasLegalConditions, HasProjectContacts, HasPriceRateSchedules, HasProjectStatus
{
}
