package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.HasContacts;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.User;

public interface ContactPersonComposite extends User, HasContacts, Relationship, PersistentComposite
{
}