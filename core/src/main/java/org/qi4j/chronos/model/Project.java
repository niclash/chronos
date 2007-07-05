package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.association.HasLeadProjectAssignee;
import org.qi4j.chronos.model.composites.association.HasLegalConditions;
import org.qi4j.chronos.model.composites.association.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.association.HasProjectAssignees;
import org.qi4j.chronos.model.composites.association.HasProjectContacts;
import org.qi4j.chronos.model.composites.association.HasProjectStatus;
import org.qi4j.chronos.model.composites.association.HasProjectTimeRange;

public interface Project extends NameWithReference, HasProjectTimeRange, HasLeadProjectAssignee, HasProjectAssignees, HasLegalConditions, HasProjectContacts, HasPriceRateSchedules, HasProjectStatus
{
}
