package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.NameWithReference;
import org.qi4j.chronos.model.composites.association.HasAccounts;
import org.qi4j.chronos.model.composites.association.HasContactPersons;

public interface CustomerComposite extends NameWithReference, HasContactPersons, HasAccounts, AddressPersistentComposite, PersistentComposite
{
}
