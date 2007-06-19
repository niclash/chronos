package org.qi4j.chronos.model;

import org.qi4j.api.annotation.ModifiedBy;

@ModifiedBy( LocationDescriptor.class)
public interface Location extends Address, ZipCode, City, State, Country, Descriptor
{
}
