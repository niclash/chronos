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

import org.qi4j.chronos.domain.model.common.name.NameState;
import org.qi4j.chronos.domain.model.user.SystemRole;
import static org.qi4j.chronos.domain.model.user.assembly.Constants.*;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.service.Activatable;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( SystemRoleBootstrap.SystemRoleBootstrapMixin.class )
interface SystemRoleBootstrap extends Activatable, ServiceComposite
{
    public class SystemRoleBootstrapMixin
        implements Activatable
    {
        @Structure private UnitOfWorkFactory uowf;

        public final void activate()
            throws Exception
        {
            UnitOfWork uow = uowf.newUnitOfWork();
            try
            {
                createSystemRoleIfNeeded( uow, SYSTEM_ADMIN_ID, SYSTEM_ADMIN_NAME );
                createSystemRoleIfNeeded( uow, ACCOUNT_ADMIN_ID, ACCOUNT_ADMIN_NAME );
                createSystemRoleIfNeeded( uow, ACCOUNT_DEVELOPER_ID, ACCOUNT_DEVELOPER_NAME );
                createSystemRoleIfNeeded( uow, CONTACT_PERSON_ID, CONTACT_PERSON_NAME );
                createSystemRoleIfNeeded( uow, STAFF_ID, STAFF_NAME );
            }
            finally
            {
                uow.complete();
            }
        }

        private void createSystemRoleIfNeeded( UnitOfWork uow, String aSystemRoleId, String aSystemRoleName )
        {
            try
            {
                uow.find( aSystemRoleId, SystemRole.class );
            }
            catch( EntityCompositeNotFoundException e )
            {
                EntityBuilder<SystemRole> roleBuilder = uow.newEntityBuilder( aSystemRoleId, SystemRole.class );
                NameState nameState = roleBuilder.stateFor( NameState.class );
                nameState.name().set( aSystemRoleName );
                roleBuilder.newInstance();
            }
        }

        public void passivate()
            throws Exception
        {
            // Do nothing
        }
    }
}