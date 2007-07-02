package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.LinkToCountry;
import org.qi4j.chronos.model.LinkToState;
import org.qi4j.library.general.model.composites.CityComposite;

/**
 * Persistable {@link org.qi4j.library.general.model.composites.CityComposite} linked to
 * {@link org.qi4j.chronos.model.composites.StateEntityComposite} and
 * {@link org.qi4j.chronos.model.composites.CountryEntityComposite}
 * <p/>
 * CityEntityComposite is an entity.
 */
public interface CityEntityComposite extends CityComposite, LinkToState, LinkToCountry, EntityComposite
{
}
