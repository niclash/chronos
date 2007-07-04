package org.qi4j.chronos.model;

import org.qi4j.library.general.model.Name;
import org.qi4j.chronos.model.composites.association.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.association.HasProjects;

public interface Account extends Name, HasPriceRateSchedules, HasProjects
{
    
}
