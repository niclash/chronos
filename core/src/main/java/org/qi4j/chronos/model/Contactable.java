package org.qi4j.chronos.model;

import java.util.List;

public interface Contactable
{
    List<Contact> getContacts();

    void addContact( Contact contact );

    void removeContact( Contact contact );
}
