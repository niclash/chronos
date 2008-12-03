package org.qi4j.chronos.domain.model.project.assignee;

import org.qi4j.chronos.domain.model.common.priceRate.PriceRate;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.composite.Optional;

/**
 * @author edward.yakop@gmail.com
 * @version 0.5
 */
public interface ProjectAssignee
{
    ProjectRole projectRole();

    boolean isProjectLeader();

    @Optional PriceRate priceRate();
    void updatePriceRate( PriceRate priceRate );

    @Optional Staff staff();
    void assignStaff( @Optional Staff staff );
}
