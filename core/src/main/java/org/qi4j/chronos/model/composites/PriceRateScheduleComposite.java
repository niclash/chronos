package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.HasPriceRates;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface PriceRateScheduleComposite extends TimeRange, HasPriceRates, PersistentComposite
{
}
