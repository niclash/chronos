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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.AccountService;

public class ChronosSession extends AuthenticatedWebSession
{
    private String userId = null;

    private String accountId = null;

    public ChronosSession( AuthenticatedWebApplication authenticatedWebApplication, Request request )
    {
        super( authenticatedWebApplication, request );
    }

    public boolean authenticate( String username, String password )
    {
        User user = ChronosWebApp.getServices().getUserService().getUser( username, password );

        if( user != null && user.getLogin().isEnabled() )
        {
            AccountService accountService = ChronosWebApp.getServices().getAccountService();

            AccountEntityComposite account = null;

            if( user instanceof Staff )
            {
                account = accountService.getAccount( (StaffEntityComposite) user );
            }
            else if( user instanceof ContactPerson )
            {
                account = accountService.getAccount( (ContactPersonEntityComposite) user );
            }

            if( account != null )
            {
                if( !account.isEnabled() )
                {
                    this.error( "Account is disabled!" );
                    return false;
                }

                accountId = account.getIdentity();
            }

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

    public String getAccountId()
    {
        return accountId;
    }

    public AccountEntityComposite getAccount()
    {
        if( accountId != null )
        {
            return ChronosWebApp.getServices().getAccountService().get( accountId );
        }
        else
        {
            return null;
        }
    }

    public boolean isAdmin()
    {
        return getUser() instanceof Admin;
    }

    public boolean isStaff()
    {
        return getUser() instanceof Staff;
    }

    public boolean isContactPerson()
    {
        return getUser() instanceof ContactPerson;
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    public Roles getRoles()
    {
        if( isSignedIn() )
        {
            User user = getUser();

            Iterator<SystemRoleEntityComposite> iterator = user.systemRoleIterator();

            Set<String> roles = new HashSet<String>();

            //TODO bp. get system role group.
            while( iterator.hasNext() )
            {
                SystemRoleEntityComposite systemRole = iterator.next();

                roles.add( systemRole.getIdentity() );
            }

            return new Roles( roles.toArray( new String[roles.size()] ) );
        }

        return null;
    }

}
