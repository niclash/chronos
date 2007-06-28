package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.LinkToCountry;
import org.qi4j.chronos.model.LinkToState;
import org.qi4j.library.general.model.composites.CityComposite;

/**
 * Persistable {@link org.qi4j.library.general.model.composites.CityComposite} linked to
 * {@link org.qi4j.chronos.model.composites.StatePersistentComposite} and
 * {@link org.qi4j.chronos.model.composites.CountryPersistentComposite}
 * <p/>
 * CityPersistentComposite is an entity.
 */
public interface CityPersistentComposite extends CityComposite, LinkToState, LinkToCountry, PersistentComposite
{
}
