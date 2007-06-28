package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.Description;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class DescriptionMixin implements Description
{
    private String description;

    public String getDescription()
    {
        return description;
    }

    @NotNullable
    public void setDescription( String description )
    {
        this.description = description;
    }
}
