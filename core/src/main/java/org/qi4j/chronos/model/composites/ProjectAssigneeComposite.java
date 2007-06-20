package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.Role;
import org.qi4j.chronos.model.HasWorkEntries;

public interface ProjectAssigneeComposite extends Role, HasWorkEntries, PersistentComposite
{
}
