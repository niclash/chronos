package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.mixins.LegalConditionMixin;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;

@ModifiedBy( { NotNullValidationModifier.class } )
@ImplementedBy( { LegalConditionMixin.class } )
public interface LegalConditionComposite extends LegalCondition, Composite
{
}
