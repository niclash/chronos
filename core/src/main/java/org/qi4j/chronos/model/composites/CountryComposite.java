package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.Country;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.library.framework.properties.PropertiesMixin;

/**
 * TODO:
 * 1. Modifier on setIsoCode() to validate iso code
 * 2. CountryValidationModifier to make sure country name and iso code matched
 * 3. Modifier on getCountryName() to return the country name based on specified Locale
 */
@ImplementedBy( { PropertiesMixin.class } )
public interface CountryComposite extends Country, PersistentComposite
{
}
