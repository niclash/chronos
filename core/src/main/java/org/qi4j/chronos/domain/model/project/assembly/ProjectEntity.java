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

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.common.task.TaskPriority;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.ProjectId;
import org.qi4j.chronos.domain.model.project.ProjectState;
import org.qi4j.chronos.domain.model.project.ProjectStatus;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.task.ProjectTask;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;

@Mixins( ProjectEntity.ProjectMixin.class )
interface ProjectEntity extends Project, EntityComposite
{
    abstract class ProjectMixin
        implements Project
    {
        private final ProjectId projectId;

        @Structure private UnitOfWorkFactory uowf;
        @Structure private TransientBuilderFactory cbf;
        @Structure private QueryBuilderFactory qbf;

        @This private ProjectState state;
        @This private Project meAsProject;

        @Service private ProjectTaskFactory taskFactory;
        @Service private ProjectAssigneeFactory assigneeFactory;

        public ProjectMixin( @This Identity identity )
        {
            String projectIdString = identity.identity().get();
            projectId = new ProjectId( projectIdString );
        }

        public final ProjectId projectId()
        {
            return projectId;
        }

        public final ProjectStatus status()
        {
            return state.projectStatus().get();
        }

        public final Period estimateTime()
        {
            return state.estimateTime().get();
        }

        public final Customer customer()
        {
            return state.customer().get();
        }

        public final Query<ContactPerson> contactPersons()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<ContactPerson> builder = qbf.newQueryBuilder( ContactPerson.class );
            builder.newQuery( state.contactPersons() );
            return builder.newQuery( uow );
        }

        public final ContactPerson primaryContactPerson()
        {
            return state.primaryContactPerson().get();
        }

        public final Query<ProjectAssignee> projectAssignees()
        {
            QueryBuilder<ProjectAssignee> builder = qbf.newQueryBuilder( ProjectAssignee.class );
            return builder.newQuery( state.projectAssignees() );
        }

        public final ProjectAssignee addProjectAssignee( ProjectRole role )
        {
            ProjectAssignee projectAssignee = assigneeFactory.create( meAsProject, role );
            state.projectAssignees().add( 0, projectAssignee );
            return projectAssignee;
        }

        public final void removeProjectAssignee( ProjectAssignee assignee )
        {
            state.projectAssignees().remove( assignee );

            UnitOfWork uow = uowf.currentUnitOfWork();
            uow.remove( assignee );
        }

        public final ProjectAssignee projectLeader()
        {
            return state.projectLeader().get();
        }

        public final Query<ProjectTask> tasks()
        {
            QueryBuilder<ProjectTask> builder = qbf.newQueryBuilder( ProjectTask.class );
            return builder.newQuery( state.tasks() );
        }

        public final void createTask( String name, String description, TaskPriority priority,
                                      User createdBy, User assignedTo )
        {
            taskFactory.create( meAsProject, name, description, priority, createdBy, assignedTo );
        }

        public final Query<LegalCondition> legalConditions()
        {
            QueryBuilder<LegalCondition> builder = qbf.newQueryBuilder( LegalCondition.class );
            return builder.newQuery( state.legalConditions() );
        }

        public final PriceRateSchedule priceRateSchedule()
        {
            return state.priceRateSchedule().get();
        }

        public final void updatePriceRateSchedule( PriceRateSchedule newSchedule )
        {
            state.priceRateSchedule().set( newSchedule );
        }

        public final boolean sameIdentityAs( Project other )
        {
            return other != null && projectId.sameValueAs( other.projectId() );
        }
    }
}