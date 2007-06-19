package org.qi4j.chronos.model;

import java.util.List;

public interface Contacts
{
    List<Contact> getContacts();

    void addContact( Contact contact );

    void removeContact( Contact contact );
}
