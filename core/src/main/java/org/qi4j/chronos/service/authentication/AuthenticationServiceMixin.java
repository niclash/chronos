/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.service.authentication;

import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.service.user.UserService;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.Service;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.UnitOfWorkFactory;

public final class AuthenticationServiceMixin
    implements AuthenticationService
{
    private @Service UserService userService;
    private @Structure UnitOfWorkFactory factory;

    public final boolean authenticate( HasLogin hasLogin, String username, String password )
    {
        validateNotNull( "hasLogin", hasLogin );
        validateNotNull( "username", username );
        validateNotNull( "password", password );

        return username.equals( hasLogin.login().get().name().get() ) &&
               password.equals( hasLogin.login().get().password().get() );
    }

    public final User authenticate( Account anAccount, String username, String password )
    {
        validateNotNull( "username", username );
        validateNotNull( "password", password );

        if( anAccount == null )
        {
            // admin
            return userService.getAdmin( username, password );
        }
        else
        {
            // normal user
            User user = userService.getUser( anAccount, username );

            if( null != user )
            {
                return authenticate( user, username, password ) ? user : null;
            }
        }

        return null;
    }
}
