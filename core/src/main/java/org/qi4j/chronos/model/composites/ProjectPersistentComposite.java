package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.composites.association.HasLegalConditions;
import org.qi4j.chronos.model.composites.association.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.association.HasProjectAssignees;
import org.qi4j.chronos.model.composites.association.HasProjectContacts;
import org.qi4j.chronos.model.composites.association.HasProjectTimeRange;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface ProjectPersistentComposite extends NameWithReferenceComposite, HasProjectTimeRange, HasProjectAssignees, HasLegalConditions, HasProjectContacts, HasPriceRateSchedules, PersistentComposite
{
}