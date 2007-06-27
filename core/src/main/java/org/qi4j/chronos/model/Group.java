package org.qi4j.chronos.model;

import java.util.List;

/**
 * TODO : Convert to COP
 */
public interface Group extends User
{
    List<SystemRole> getMembers();

    List<SystemRole> getRequiredMembers();
}
