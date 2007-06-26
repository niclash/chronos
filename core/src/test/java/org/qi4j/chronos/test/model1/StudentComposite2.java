package org.qi4j.chronos.test.model1;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.Composite;
import org.qi4j.chronos.model.modifiers.StringLengthValidationModifier;

@ImplementedBy({ AddressImpl.class, SchoolImpl.class})
@ModifiedBy({ StringLengthValidationModifier.class })
public interface StudentComposite2 extends Address, School, Composite
{

}
