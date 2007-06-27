package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.HasLegalConditions;
import org.qi4j.chronos.model.HasPriceRateSchedules;
import org.qi4j.chronos.model.HasProjectTimeRange;
import org.qi4j.chronos.model.NameWithReference;
import org.qi4j.chronos.model.HasProjectAssignees;
import org.qi4j.chronos.model.HasProjectContacts;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface ProjectComposite extends NameWithReference, HasProjectTimeRange, HasProjectAssignees, HasLegalConditions, HasProjectContacts, HasPriceRateSchedules, PersistentComposite
{
}