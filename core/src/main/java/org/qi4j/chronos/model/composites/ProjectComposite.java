package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.HasLegalConditions;
import org.qi4j.chronos.model.HasPriceRateSchedules;
import org.qi4j.chronos.model.HasProjectTimeRange;
import org.qi4j.chronos.model.ProjectName;
import org.qi4j.chronos.model.HasProjectAssignees;
import org.qi4j.chronos.model.HasProjectContacts;

public interface ProjectComposite extends ProjectName, HasProjectTimeRange, HasProjectAssignees, HasLegalConditions, HasProjectContacts, HasPriceRateSchedules, PersistentComposite
{
}