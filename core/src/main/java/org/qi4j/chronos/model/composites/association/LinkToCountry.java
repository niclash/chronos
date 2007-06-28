package org.qi4j.chronos.model.composites.association;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.CountryPersistentComposite;
import org.qi4j.chronos.model.modifiers.NotNullable;

/**
 * Represents one-to-one association with {@link org.qi4j.chronos.model.composites.CityPersistentComposite}
 */
public interface LinkToCountry extends Serializable
{
    CountryPersistentComposite getCountry();

    @NotNullable
    void setCountry( CountryPersistentComposite country );
}
