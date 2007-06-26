package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.ImplementedBy;

@ImplementedBy(ValidatableImpl.class)
public interface Validatable
{
    void validate() throws ValidationException;
}
