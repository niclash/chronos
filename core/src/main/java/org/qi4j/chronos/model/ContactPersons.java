package org.qi4j.chronos.model;

import java.util.List;

public interface ContactPersons
{
    List<ContactPerson> getContactPersons();

    void addContactPerson(ContactPerson contactPerson);

    void removeContactPerson(ContactPerson contactPerson);
}
