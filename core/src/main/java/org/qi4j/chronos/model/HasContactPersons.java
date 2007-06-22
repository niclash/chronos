package org.qi4j.chronos.model;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.ContactPersonComposite;

public interface HasContactPersons
{
    Iterator<ContactPersonComposite> contactPersonIterator();

    void addContactPerson( ContactPersonComposite contactPerson);

    void removeContactPerson( ContactPersonComposite contactPerson);
}
