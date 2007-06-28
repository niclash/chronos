package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.mixins.MoneyDescriptorMixin;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Money;

/**
 * ValueObject for {@link org.qi4j.library.general.model.Money} that provides the service to display
 * the formatted money value.
 */
@ImplementedBy( { PropertiesMixin.class, MoneyDescriptorMixin.class} )
public interface MoneyComposite extends Money, Descriptor, Composite
{
}