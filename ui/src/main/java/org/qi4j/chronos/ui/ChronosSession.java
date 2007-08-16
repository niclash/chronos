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
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;

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
        if( username.equals( "admin" ) && password.equals( "admin" ) )
        {
            userId = "admin";
            return true;
        }

        return false;
    }

    public User getUser()
    {
        if( userId != null )
        {
            Staff staff = ChronosWebApp.newInstance( StaffEntityComposite.class );

            Login login = ChronosWebApp.newInstance( LoginComposite.class );

            login.setName( "admin" );
            login.setPassword( "admin" );

            staff.setLastName( "admin" );
            staff.setFirstName( "admin" );

            staff.setLogin( login );

            return staff;
        }

        return null;
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    public Roles getRoles()
    {
        if( isSignedIn() )
        {
            return ADMIN;
        }

        return null;
    }
}
