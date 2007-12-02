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
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.UserService;
import org.qi4j.entity.Identity;

public class ChronosSession extends AuthenticatedWebSession
{
    private String userId = null;
    private String accountId = null;
    private SystemRoleResolver roleResolver;

    public ChronosSession( AuthenticatedWebApplication authenticatedWebApplication, Request request )
    {
        super( authenticatedWebApplication, request );
    }

    public static ChronosSession get()
    {
        return (ChronosSession) Session.get();
    }

    public void setAccountId( String accountId )
    {
        this.accountId = accountId;
    }

    private UserService getUserService()
    {
        return ChronosWebApp.getServices().getUserService();
    }

    public boolean authenticate( String username, String password )
    {
        AccountEntityComposite account = null;

        if( accountId != null )
        {
            account = ChronosWebApp.getServices().getAccountService().get( accountId );

            if( !account.getEnabled() )
            {
                this.error( "Account[" + account.getName() + "] is disabled." );
                return false;
            }
        }

        User user;

        if( account != null )
        {
            user = getUserService().getUser( account, username, password );
        }
        else
        {
            user = getUserService().getUser( username, password );
        }

        if( user != null )
        {
            if( !user.getLogin().getEnabled() )
            {
                this.error( "User login is disabled." );
                return false;
            }

            userId = ( (Identity) user ).getIdentity();

            roleResolver = new SystemRoleResolver( user );

            return true;
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

    public boolean isStaff()
    {
        return getUser() instanceof Staff;
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

    public SystemRoleResolver getSystemRoleResolver()
    {
        return roleResolver;
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    public Roles getRoles()
    {
        return roleResolver.getRoles();
    }
}
