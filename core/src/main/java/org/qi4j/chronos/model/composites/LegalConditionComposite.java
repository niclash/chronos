package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface LegalConditionComposite extends LegalCondition, Composite
{
}
