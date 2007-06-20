package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.AddressDescriptor;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.Descriptor;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.ZipCode;

@ModifiedBy( AddressDescriptor.class)
public interface AddressComposite extends Address, ZipCode, City, State, Country, Descriptor, PersistentComposite
{
}
