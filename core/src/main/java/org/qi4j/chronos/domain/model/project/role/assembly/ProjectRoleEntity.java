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
package org.qi4j.chronos.domain.model.project.role.assembly;

import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleId;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.AggregateEntity;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectRoleEntity.ProjectRoleMixin.class )
interface ProjectRoleEntity extends ProjectRole, AggregateEntity
{
    abstract class ProjectRoleMixin
        implements ProjectRole
    {
        @This private ProjectRoleState state;

        private final ProjectRoleId projectRoleId;

        public ProjectRoleMixin( @This Identity identity )
        {
            projectRoleId = new ProjectRoleId( identity.identity().get() );
        }

        public final ProjectRoleId projectRoleId()
        {
            return projectRoleId;
        }

        public final boolean sameIdentityAs( ProjectRole other )
        {
            return other != null && projectRoleId.sameValueAs( other.projectRoleId() );
        }
    }
}
