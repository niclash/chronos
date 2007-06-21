package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.Description;
import org.qi4j.chronos.model.HasComments;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.Title;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface WorkEntryComposite extends Title, Description, TimeRange, HasComments, PersistentComposite
{
}
