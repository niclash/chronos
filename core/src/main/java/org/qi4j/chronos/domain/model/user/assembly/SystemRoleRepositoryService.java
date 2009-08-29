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

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.NoSuchEntityException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.user.SystemRole;
import org.qi4j.chronos.domain.model.user.SystemRoleId;
import org.qi4j.chronos.domain.model.user.SystemRoleRepository;
import static org.qi4j.chronos.domain.model.user.assembly.Constants.*;

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
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                return uow.get( SystemRole.class, id );
            }
            catch( NoSuchEntityException e )
            {
                return null;
            }
        }

        public SystemRole contactPerson()
        {
            return findSystemRoleById( CONTACT_PERSON_ID );
        }

        public SystemRole staff()
        {
            return findSystemRoleById( STAFF_ID );
        }

        public SystemRole developer()
        {
            return findSystemRoleById( ACCOUNT_DEVELOPER_ID );
        }

        public SystemRole find( SystemRoleId systemRoleId )
        {
            return findSystemRoleById( systemRoleId.idString() );
        }
    }
}
