package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.Gender;
import org.qi4j.chronos.model.PersonName;
import org.qi4j.library.framework.properties.PropertiesMixin;

/**
 * Generic person composite with name and gender
 */
@ImplementedBy( { PropertiesMixin.class } )
public interface PersonComposite extends PersonName, Gender, Composite
{
}
