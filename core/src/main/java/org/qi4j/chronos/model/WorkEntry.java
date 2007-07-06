package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.association.HasComments;
import org.qi4j.library.general.model.Description;

public interface WorkEntry extends Title, Description, TimeRange, HasComments
{
}
