package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.ContactType;
import org.qi4j.chronos.model.RegularExpression;

public interface ContactTypeComposite extends ContactType, RegularExpression, PersistentComposite
{
}
