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
package org.qi4j.chronos.domain.model.user.assembly;

import org.qi4j.chronos.domain.model.user.SystemRole;
import org.qi4j.chronos.domain.model.user.SystemRoleId;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.Identity;
import org.qi4j.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( SystemRoleEntity.SystemRoleMixin.class )
interface SystemRoleEntity extends SystemRole, EntityComposite
{
    abstract class SystemRoleMixin
        implements SystemRole
    {
        private final SystemRoleId systemRoleId;

        public SystemRoleMixin( @This Identity identity )
        {
            String idString = identity.identity().get();
            systemRoleId = new SystemRoleId( idString );
        }

        public SystemRoleId systemRoleId()
        {
            return systemRoleId;
        }
    }
}
