package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.Descriptor;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.MoneyDescriptor;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class, MoneyDescriptor.class} )
public interface MoneyComposite extends Money, Descriptor, Composite
{
}
