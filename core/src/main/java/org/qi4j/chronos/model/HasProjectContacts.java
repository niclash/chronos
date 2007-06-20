package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.ContactPersonComposite;

public interface HasProjectContacts extends HasContactPersons
{
    ContactPersonComposite getPrimaryContactPerson();

    void setPrimaryContactPerson(ContactPersonComposite contactPersonComposite);
}
