package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.api.persistence.Lifecycle;

public class LifecycleValidationModifier
    implements Lifecycle
{
    @Uses Validatable validation;
    @Modifies Lifecycle next;

    public void create()
    {
        validation.validate();

        next.create();
    }

    public void delete()
    {
        
    }
}

