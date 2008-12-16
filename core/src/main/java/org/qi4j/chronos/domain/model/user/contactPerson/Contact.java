package org.qi4j.chronos.domain.model.user.contactPerson;

import org.qi4j.api.entity.Identity;
import org.qi4j.api.property.Property;


public interface Contact extends Identity
{
    Property<String> contactValue();

    Property<String> contactType();
}
