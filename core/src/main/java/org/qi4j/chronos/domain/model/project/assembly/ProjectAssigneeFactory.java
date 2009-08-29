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

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssigneeState;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;

@Mixins( ProjectAssigneeFactory.ProjectAssigneeFactoryMixin.class )
interface ProjectAssigneeFactory extends ServiceComposite
{
    ProjectAssignee create( Project meAsProject, ProjectRole role );

    abstract class ProjectAssigneeFactoryMixin
        implements ProjectAssigneeFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public ProjectAssignee create( Project project, ProjectRole role )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            EntityBuilder<ProjectAssignee> builder = uow.newEntityBuilder( ProjectAssignee.class );
            ProjectAssigneeState state = builder.instanceFor( ProjectAssigneeState.class );
            state.projectRole().set( role );
            state.project().set( project );
            return builder.newInstance();
        }
    }
}
