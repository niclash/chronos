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

import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.project.task.ProjectTask;
import org.qi4j.chronos.domain.model.project.task.ProjectTaskState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;

@Mixins( ProjectTaskEntity.ProjectTaskMixin.class )
interface ProjectTaskEntity extends ProjectTask, EntityComposite
{
    abstract class ProjectTaskMixin
        implements ProjectTask
    {
        @This private ProjectTaskState state;

        @Structure private UnitOfWorkFactory uowf;

        public final Project project()
        {
            return state.project().get();
        }

        public final void reAssignTo( ProjectAssignee assignee )
        {
            if( project().equals( assignee.project() ) )
            {
                state.assignedTo().set( assignee.staff() );
            }
            // TODO: The assignee is not part of this project.
        }
    }
}
