package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasAccounts;
import org.qi4j.chronos.model.associations.HasAddress;
import org.qi4j.chronos.model.associations.HasContactPersons;

public interface Customer extends NameWithReference, HasContactPersons, HasAccounts, HasAddress
{
}

