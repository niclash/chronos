package org.qi4j.chronos.model.composites.association;

import org.qi4j.chronos.model.composites.ContactPersonComposite;
import java.io.Serializable;

public interface HasProjectContacts extends HasContactPersons, Serializable
{
    ContactPersonComposite getPrimaryContactPerson();

    void setPrimaryContactPerson(ContactPersonComposite contactPersonComposite);
}

