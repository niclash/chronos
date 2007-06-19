package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.chronos.model.CustomerName;
import org.qi4j.chronos.model.FullAddress;
import org.qi4j.chronos.model.ContactPersons;

public interface CustomerComposite extends CustomerName, FullAddress, ContactPersons, Composite
{
}
