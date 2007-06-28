package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.NameWithReference;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;

@ModifiedBy({ NotNullValidationModifier.class })
public interface NameWithReferenceComposite extends NameWithReference, Composite
{
}
