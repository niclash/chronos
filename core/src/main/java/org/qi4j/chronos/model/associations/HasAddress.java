package org.qi4j.chronos.model.associations;

import org.qi4j.library.general.model.Address;

public interface HasAddress
{
    Address getAddress();

    void setAddress( Address address );
}
