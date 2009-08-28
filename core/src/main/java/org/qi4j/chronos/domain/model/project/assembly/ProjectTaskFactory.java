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

import java.util.Date;
import org.qi4j.chronos.domain.model.common.task.TaskPriority;
import static org.qi4j.chronos.domain.model.common.task.TaskStatus.open;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.task.ProjectTask;
import org.qi4j.chronos.domain.model.project.task.ProjectTaskState;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.service.ServiceComposite;

@Mixins( ProjectTaskFactory.ProjectTaskFactoryMixin.class )
interface ProjectTaskFactory extends ServiceComposite
{
    ProjectTask create( Project project,
                        String title, String description, TaskPriority priority,
                        User reportedBy, User assignedTo );

    abstract class ProjectTaskFactoryMixin
        implements ProjectTaskFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final ProjectTask create( Project project,
                                         String title, String description, TaskPriority priority,
                                         User createdBy, User assignedTo )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            EntityBuilder<ProjectTask> builder = uow.newEntityBuilder( ProjectTask.class );
            ProjectTaskState state = builder.instanceFor( ProjectTaskState.class );

            state.title().set( title );
            state.description().set( description );
            state.createdBy().set( createdBy );
            state.assignedTo().set( assignedTo );
            state.createdDate().set( new Date() );
            state.project().set( project );
            state.priority().set( priority );
            state.status().set( open );

            return builder.newInstance();
        }
    }
}
