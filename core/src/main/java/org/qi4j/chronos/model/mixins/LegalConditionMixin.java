package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class LegalConditionMixin implements LegalCondition
{
    private String legalCondition;

    @NotNullable
    public void setLegalCondition( String legalCondition )
    {
        this.legalCondition = legalCondition;
    }

    public String getLegalCondition()
    {
        return legalCondition;
    }
}
