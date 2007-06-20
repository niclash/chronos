package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.UserComposite;

/**
 * TODO : Convert to COP
 */
public interface Group extends UserComposite
{
    List<SystemRole> getMembers();

    List<SystemRole> getRequiredMembers();
}
