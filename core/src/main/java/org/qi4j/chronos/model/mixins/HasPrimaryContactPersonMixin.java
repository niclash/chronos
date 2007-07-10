package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.associations.HasPrimaryContactPerson;

public class HasPrimaryContactPersonMixin implements HasPrimaryContactPerson
{
    private ContactPerson primaryContactPerson;

    public ContactPerson getPrimaryContactPerson()
    {
        return primaryContactPerson;
    }

    public void setPrimaryContactPerson( ContactPerson contactPerson )
    {
        this.primaryContactPerson = contactPerson;
    }
}
