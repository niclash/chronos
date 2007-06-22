package org.qi4j.chronos.model;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.ContactComposite;

public interface HasContacts
{
    void addContact( ContactComposite contact );

    void removeContact( ContactComposite contact );

    Iterator<ContactComposite> contactIterator();
}
