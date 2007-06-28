package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.NameWithReference;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class NameWithReferenceMixin implements NameWithReference
{
    private String name;
    private String reference;

    @NotNullable
    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getReference()
    {
        return reference;
    }

    @NotNullable
    public void setReference( String reference )
    {
        this.reference = reference;
    }
}
