package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRate;
import org.qi4j.chronos.model.associations.HasStaff;
import org.qi4j.chronos.model.associations.IsLead;
import org.qi4j.entity.Identity;

/**
 * Generic interface for Project Assignee
 */
public interface ProjectAssignee extends HasPriceRate, HasStaff, IsLead, Identity
{
}
