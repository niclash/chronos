package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.chronos.model.associations.HasProjectRole;
import org.qi4j.chronos.model.associations.HasStaff;
import org.qi4j.chronos.model.associations.HasWorkEntries;

/**
 * Generic interface for Project Assignee
 */
public interface ProjectAssignee extends HasProjectRole, HasPriceRates, HasWorkEntries, HasStaff
{
}
