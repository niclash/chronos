package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.ContactPersonComposite;

public interface HasContactPersons
{
    List<ContactPersonComposite> getContactPersons();

    void addContactPerson( ContactPersonComposite contactPerson);

    void removeContactPerson( ContactPersonComposite contactPerson);
}
