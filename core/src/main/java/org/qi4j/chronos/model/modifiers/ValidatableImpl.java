package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.ModifiedBy;

@ModifiedBy(LifecycleValidationModifier.class)
public class ValidatableImpl implements Validatable
{
    public void validate() throws ValidationException
    {
        //no operation, all work is done by Modifier.
    }
}
