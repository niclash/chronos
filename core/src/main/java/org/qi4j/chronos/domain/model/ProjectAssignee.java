package org.qi4j.chronos.domain.model;

import org.qi4j.chronos.domain.model.associations.HasPriceRate;
import org.qi4j.chronos.domain.model.associations.HasStaff;
import org.qi4j.chronos.domain.model.associations.IsLead;
import org.qi4j.entity.Identity;

/**
 * Generic interface for Project Assignee
 */
public interface ProjectAssignee extends HasPriceRate, HasStaff, IsLead, Identity
{
}
