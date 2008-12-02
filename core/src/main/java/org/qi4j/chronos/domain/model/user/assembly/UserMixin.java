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

import org.qi4j.chronos.domain.model.user.Login;
import org.qi4j.chronos.domain.model.user.SystemRole;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.chronos.domain.model.user.UserDetail;
import org.qi4j.chronos.domain.model.user.UserId;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
final class UserMixin
    implements User
{
    @This private UserState state;
    @Structure private CompositeBuilderFactory cbf;
    @Structure private UnitOfWorkFactory uowf;

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
            CompositeBuilder<Login> loginBuilder = cbf.newCompositeBuilder( Login.class );
            loginBuilder.use( state );
            login = loginBuilder.newInstance();
        }
        return login;
    }

    public final UserDetail userDetail()
    {
        if( userDetail == null )
        {
            CompositeBuilder<UserDetail> detailBuilder = cbf.newCompositeBuilder( UserDetail.class );
            detailBuilder.use( state );
            userDetail = detailBuilder.newInstance();
        }
        return userDetail;
    }

    public final Query<SystemRole> systemRoles()
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory qbf = uow.queryBuilderFactory();
        QueryBuilder<SystemRole> systemRoleBuilder = qbf.newQueryBuilder( SystemRole.class );
        return systemRoleBuilder.newQuery( state.systemRoles() );
    }

    public final void addSystemRole( SystemRole role )
    {
        state.systemRoles().add( role );
    }

    public final void removeSystemRole( SystemRole role )
    {
        state.systemRoles().remove( role );
    }
}
