package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.CustomerName;
import org.qi4j.chronos.model.HasAccounts;
import org.qi4j.chronos.model.HasContactPersons;

public interface CustomerComposite extends CustomerName, HasContactPersons, HasAccounts, AddressComposite, PersistentComposite
{
}
