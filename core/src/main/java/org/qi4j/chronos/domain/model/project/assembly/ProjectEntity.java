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
import org.qi4j.chronos.domain.model.project.ProjectId;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.Identity;
import org.qi4j.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectEntity.ProjectMixin.class )
interface ProjectEntity extends Project, EntityComposite
{
    abstract class ProjectMixin
        implements Project
    {
        private final ProjectId projectId;

        public ProjectMixin( @This Identity identity )
        {
            String projectIdString = identity.identity().get();
            projectId = new ProjectId( projectIdString );
        }

        public final ProjectId projectId()
        {
            return projectId;
        }

        public final boolean sameIdentityAs( Project other )
        {
            return other != null && projectId.sameValueAs( other.projectId() );
        }
    }
}
