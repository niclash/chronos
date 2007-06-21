package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.State;
import org.qi4j.library.framework.properties.PropertiesMixin;

/**
 * TODO:
 * 1. CityCompositeValidator to make sure the City has valid State and Country 
 */
@ImplementedBy( { PropertiesMixin.class } )
public interface CityComposite extends City, State, Country, PersistentComposite
{
    
}
