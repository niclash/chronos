package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.chronos.model.associations.HasRole;
import org.qi4j.chronos.model.associations.HasWorkEntries;

/**
 * Generic interface for Project Assignee
 */
public interface ProjectAssignee extends HasRole, HasPriceRates, HasWorkEntries
{
}
