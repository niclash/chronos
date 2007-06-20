package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.HasLegalConditions;
import org.qi4j.chronos.model.HasPriceRateSchedules;
import org.qi4j.chronos.model.Name;

public interface ProjectComposite extends Name, HasLegalConditions, HasPriceRateSchedules, PersistentComposite
{
}