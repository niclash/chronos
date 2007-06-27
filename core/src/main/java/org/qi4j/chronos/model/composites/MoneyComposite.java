package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.MoneyDescriptor;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Money;

@ImplementedBy( { PropertiesMixin.class, MoneyDescriptor.class} )
public interface MoneyComposite extends Money, Descriptor, Composite
{
}
