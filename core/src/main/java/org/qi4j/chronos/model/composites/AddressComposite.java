package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.AddressDescriptor;
import org.qi4j.chronos.model.Descriptor;
import org.qi4j.chronos.model.ZipCode;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ModifiedBy( { AddressDescriptor.class } )
@ImplementedBy( { PropertiesMixin.class } )
public interface AddressComposite extends Address, ZipCode, CityComposite, Descriptor, PersistentComposite
{
}
