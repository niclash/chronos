package org.qi4j.chronos.test.model1;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.Composite;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;

@ImplementedBy({ AddressImpl.class, SchoolImpl.class})
@ModifiedBy({ NotNullValidationModifier.class })
public interface StudentComposite1 extends Address, School, Composite
{
    
}
