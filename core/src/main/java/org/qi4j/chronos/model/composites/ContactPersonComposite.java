package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.HasContacts;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.composites.UserComposite;

public interface ContactPersonComposite extends UserComposite, HasContacts, Relationship, PersistentComposite
{
}