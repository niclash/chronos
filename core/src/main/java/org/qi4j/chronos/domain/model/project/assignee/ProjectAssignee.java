package org.qi4j.chronos.domain.model.project.assignee;

import org.qi4j.chronos.domain.model.common.priceRate.PriceRate;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.staff.Staff;
import org.qi4j.api.common.Optional;

public interface ProjectAssignee
{
    Project project();

    ProjectRole projectRole();

    @Optional PriceRate priceRate();

    void updatePriceRate( PriceRate priceRate );

    @Optional Staff staff();

    void assignStaff( @Optional Staff staff );
}
