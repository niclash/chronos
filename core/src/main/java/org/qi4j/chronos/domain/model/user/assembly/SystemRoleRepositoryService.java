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
import org.qi4j.chronos.domain.model.user.SystemRoleRepository;
import static org.qi4j.chronos.domain.model.user.assembly.Constants.*;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( SystemRoleRepositoryService.SystemRoleRepositoryMixin.class )
interface SystemRoleRepositoryService extends SystemRoleRepository, ServiceComposite
{
    class SystemRoleRepositoryMixin
        implements SystemRoleRepository
    {
        @Structure private UnitOfWorkFactory uowf;

        public SystemRole systemAdmin()
        {
            return findSystemRoleById( SYSTEM_ADMIN_ID );
        }

        private SystemRole findSystemRoleById( String id )
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            try
            {
                SystemRole systemRole = uow.find( id, SystemRole.class );
                uow.pause();
                return systemRole;
            }
            catch( EntityCompositeNotFoundException e )
            {
                uow.discard();
                return null;
            }
        }

        public final SystemRole contactPerson()
        {
            return findSystemRoleById( CONTACT_PERSON_ID );
        }

        public final SystemRole staff()
        {
            return findSystemRoleById( STAFF_ID );
        }

        public final SystemRole developer()
        {
            return findSystemRoleById( ACCOUNT_DEVELOPER_ID );
        }

        public final SystemRole find( SystemRoleId systemRoleId )
        {
            return findSystemRoleById( systemRoleId.idString() );
        }
    }
}
