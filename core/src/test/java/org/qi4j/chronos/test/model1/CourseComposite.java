package org.qi4j.chronos.test.model1;

import java.io.Serializable;
import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.modifiers.TimeRangeValidatorModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
@ModifiedBy({ TimeRangeValidatorModifier.class })
public interface CourseComposite extends Composite, TimeRange, Serializable
{

}
