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

import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.project.ProjectDetail;
import org.qi4j.chronos.domain.model.project.ProjectState;
import org.qi4j.chronos.domain.model.project.ProjectStatus;
import org.qi4j.chronos.domain.model.project.assignee.ProjectAssignee;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.Optional;
import org.qi4j.injection.scope.Uses;
import org.qi4j.query.Query;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectDetailComposite.ProjectDetailMixin.class )
interface ProjectDetailComposite extends ProjectDetail, Composite
{
    final class ProjectDetailMixin
        implements ProjectDetail
    {
        @Uses private ProjectState state;

        public final String name()
        {
            return state.name().get();
        }

        @Optional
        public final String referenceName()
        {
            return state.referenceName().get();
        }

        public final void changeReferenceName( @Optional String newReferenceName )
        {
            state.referenceName().set( newReferenceName );
        }

        public final ProjectStatus status()
        {
            return state.projectStatus().get();
        }

        public final Period estimateTime()
        {
            return state.estimateTime().get();
        }

        public final Period actualTime()
        {
            return state.actualTime().get();
        }

        public Customer customer()
        {
            return null;
        }

        public Query<ContactPerson> contactPersons()
        {
            return null;
        }

        public ContactPerson primaryContactPerson()
        {
            return null;
        }

        public Query<ProjectAssignee> projectAssignees()
        {
            return null;
        }

        public ProjectAssignee projectLeader()
        {
            return null;
        }
    }
}