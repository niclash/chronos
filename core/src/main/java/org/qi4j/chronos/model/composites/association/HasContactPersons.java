package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.ContactPersonComposite;

public interface HasContactPersons extends Serializable
{
    Iterator<ContactPersonComposite> contactPersonIterator();

    void addContactPerson( ContactPersonComposite contactPerson);

    void removeContactPerson( ContactPersonComposite contactPerson);
}
