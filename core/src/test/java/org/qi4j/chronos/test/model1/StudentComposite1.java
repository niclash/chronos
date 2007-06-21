package org.qi4j.chronos.test.model1;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.Composite;
import org.qi4j.chronos.model.modifier.NotNullValidatorModifier;

@ImplementedBy({ AddressImpl.class, SchoolImpl.class})
@ModifiedBy({ NotNullValidatorModifier.class })
public interface StudentComposite1 extends Address, School, Composite
{
    
}
