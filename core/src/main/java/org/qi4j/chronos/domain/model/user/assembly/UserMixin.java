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

import org.qi4j.api.composite.TransientBuilder;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.chronos.domain.model.user.Login;
import org.qi4j.chronos.domain.model.user.SystemRole;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.chronos.domain.model.user.UserDetail;
import org.qi4j.chronos.domain.model.user.UserId;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
final class UserMixin
    implements User
{
    @This private UserState state;
    @Structure private TransientBuilderFactory cbf;
    @Structure private QueryBuilderFactory qbf;

    private final UserId userId;
    private UserDetail userDetail;
    private Login login;

    public UserMixin( @This Identity identity )
    {
        userId = new UserId( identity.identity().get() );
    }

    public UserId userId()
    {
        return userId;
    }

    public final Login login()
    {
        if( login == null )
        {
            TransientBuilder<Login> loginBuilder = cbf.newTransientBuilder( Login.class );
            loginBuilder.use( state );
            login = loginBuilder.newInstance();
        }
        return login;
    }

    public final UserDetail userDetail()
    {
        if( userDetail == null )
        {
            TransientBuilder<UserDetail> detailBuilder = cbf.newTransientBuilder( UserDetail.class );
            detailBuilder.use( state );
            userDetail = detailBuilder.newInstance();
        }
        return userDetail;
    }

    public final Query<SystemRole> systemRoles()
    {
        QueryBuilder<SystemRole> systemRoleBuilder = qbf.newQueryBuilder( SystemRole.class );
        return systemRoleBuilder.newQuery( state.systemRoles() );
    }

    public final void addSystemRole( SystemRole role )
    {
        state.systemRoles().add( 0, role );
    }

    public final void removeSystemRole( SystemRole role )
    {
        state.systemRoles().remove( role );
    }
}
