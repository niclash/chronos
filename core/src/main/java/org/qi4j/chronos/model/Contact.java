package org.qi4j.chronos.model;

import org.qi4j.entity.Identity;
import org.qi4j.property.Property;

public interface Contact extends Identity
{
    Property<String> contactValue();

    Property<String> contactType();
}
