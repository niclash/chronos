/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.chronos.domain.model.project.assembly;

import org.qi4j.api.common.Optional;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRate;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssigneeState;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.staff.Staff;

@Mixins( ProjectAssigneeEntity.ProjectAssigneeMixin.class )
interface ProjectAssigneeEntity extends ProjectAssignee, EntityComposite
{
    class ProjectAssigneeMixin
        implements ProjectAssignee
    {
        @This private ProjectAssigneeState state;

        public Project project()
        {
            return state.project().get();
        }

        public ProjectRole projectRole()
        {
            return state.projectRole().get();
        }

        public PriceRate priceRate()
        {
            return state.priceRate().get();
        }

        public void updatePriceRate( PriceRate priceRate )
        {
            state.priceRate().set( priceRate );
        }

        @Optional
        public Staff staff()
        {
            return state.staff().get();
        }

        public void assignStaff( @Optional Staff staff )
        {
            state.staff().set( staff );
        }
    }
}
