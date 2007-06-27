package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.HasPriceRateSchedules;
import org.qi4j.chronos.model.HasProjects;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface AccountComposite extends Name, HasPriceRateSchedules, HasProjects, PersistentComposite
{
}
