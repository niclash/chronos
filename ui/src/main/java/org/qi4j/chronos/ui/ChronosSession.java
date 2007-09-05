/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.ui;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.User;

//TODO bp. fix authentication and getRoles
public class ChronosSession extends AuthenticatedWebSession
{
    private static final Roles ADMIN = new Roles( Roles.ADMIN );

    private String userId = null;

    public ChronosSession( AuthenticatedWebApplication authenticatedWebApplication, Request request )
    {
        super( authenticatedWebApplication, request );
    }

    public boolean authenticate( String username, String password )
    {
        User user = ChronosWebApp.getServices().getUserService().getUser( username, password );

        if( user != null && user.getLogin().isEnabled() )
        {
            userId = ( (Identity) user ).getIdentity();

            return true;
        }
        else
        {
            this.error( "Login account is disabled." );
        }

        return false;
    }

    public User getUser()
    {
        return ChronosWebApp.getServices().getUserService().get( userId );
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    public Roles getRoles()
    {
        //TODO bp. get the system role from user.
        if( isSignedIn() )
        {
            return ADMIN;
        }

        return null;
    }
}
