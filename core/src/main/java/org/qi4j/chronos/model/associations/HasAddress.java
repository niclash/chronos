package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.library.general.model.Address;

public interface HasAddress extends Serializable
{
    Address getAddress();

    void setAddress( Address address );
}
