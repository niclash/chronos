package org.qi4j.chronos.domain.model.user.contactPerson;

import org.qi4j.entity.Identity;
import org.qi4j.property.Property;

public interface Contact extends Identity
{
    Property<String> contactValue();

    Property<String> contactType();
}
