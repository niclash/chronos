package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.chronos.model.CustomerName;
import org.qi4j.chronos.model.Location;
import org.qi4j.chronos.model.ContactPersons;

public interface CustomerComposite extends CustomerName, Location, ContactPersons, Composite
{
}
