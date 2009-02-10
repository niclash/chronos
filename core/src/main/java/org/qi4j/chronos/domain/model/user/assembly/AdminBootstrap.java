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

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.Activatable;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.NoSuchEntityException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.user.SystemRole;
import org.qi4j.chronos.domain.model.user.SystemRoleRepository;
import org.qi4j.chronos.domain.model.user.admin.Admin;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( AdminBootstrap.AdminBootstrapMixin.class )
public interface AdminBootstrap extends Activatable, ServiceComposite
{
    class AdminBootstrapMixin
        implements Activatable
    {
        private static final String ID_ADMIN = Admin.class.getName() + ".Admin";

        @Structure private UnitOfWorkFactory uowf;
        @Service private SystemRoleRepository systemRoleRepository;

        // Don't remove, we need the system role to be bootstrap first
        @Service private SystemRoleBootstrap bootstrap;

        public final void activate()
            throws Exception
        {
            UnitOfWork uow = uowf.newUnitOfWork();
            if( !isSystemAdminUserExists( uow ) )
            {
                createSystemAdminUser( uow );
            }
        }

        private boolean isSystemAdminUserExists( UnitOfWork uow )
        {
            try
            {
                uow.find( ID_ADMIN, Admin.class );
                uow.discard();
                return true;
            }
            catch( NoSuchEntityException e )
            {
                return false;
            }
        }

        private void createSystemAdminUser( UnitOfWork uow )
            throws UnitOfWorkCompletionException
        {
            EntityBuilder<Admin> adminBuilder = uow.newEntityBuilder( ID_ADMIN, Admin.class );
            UserState userState = adminBuilder.stateFor( UserState.class );
            userState.firstName().set( "Admin" );
            userState.lastName().set( "Admin" );
            userState.isLoginEnabled().set( true );
            userState.loginName().set( "admin" );
            userState.loginPassword().set( "admin" );

            SystemRole systemAdminRole = systemRoleRepository.systemAdmin();
            userState.systemRoles().add( systemAdminRole );
            adminBuilder.newInstance();

            uow.complete();
        }

        public final void passivate()
            throws Exception
        {
            // Do nothing
        }
    }
}
