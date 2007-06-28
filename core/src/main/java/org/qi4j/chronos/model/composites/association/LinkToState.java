package org.qi4j.chronos.model.composites.association;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.StatePersistentComposite;
import org.qi4j.chronos.model.modifiers.NotNullable;

/**
 * Represents one-to-one association with {@link org.qi4j.chronos.model.composites.CityPersistentComposite}
 */
public interface LinkToState extends Serializable
{
    StatePersistentComposite getState();

    @NotNullable
    void setState( StatePersistentComposite state );
}
