package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.library.general.model.Name;

public interface Account extends Name, HasPriceRateSchedules, HasProjects
{
    
}
