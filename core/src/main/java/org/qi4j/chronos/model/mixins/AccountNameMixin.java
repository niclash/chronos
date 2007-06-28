package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;
import org.qi4j.library.general.model.Name;

@ModifiedBy( { NotNullValidationModifier.class } )
public class AccountNameMixin implements Name
{
    private String name;

    @NotNullable
    public void setName( String name )
    {
        name = name;
    }

    public String getName()
    {
        return name;
    }
}
