package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.HasAccounts;
import org.qi4j.chronos.model.HasContactPersons;
import org.qi4j.chronos.model.NameWithReference;

public interface CustomerComposite extends NameWithReference, HasContactPersons, HasAccounts, AddressComposite, PersistentComposite
{
}
