package org.qi4j.chronos.model;

import java.util.List;

public interface Group extends User
{
    List<SystemRole> getMembers();

    List<SystemRole> getRequiredMembers();
}
