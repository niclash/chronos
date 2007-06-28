package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.Title;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class TitleMixin implements Title
{
    private String title;

    @NotNullable
    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
