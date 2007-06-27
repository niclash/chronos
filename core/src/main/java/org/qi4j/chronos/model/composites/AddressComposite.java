package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.AddressDescriptor;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.entities.AddressEntity;

@ModifiedBy( { AddressDescriptor.class } )
@ImplementedBy( { PropertiesMixin.class } )
public interface AddressComposite extends AddressEntity, Descriptor, PersistentComposite
{
}
