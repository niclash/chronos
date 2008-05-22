/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2008, Edward Yakop. All Rights Reserved.
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
package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.service.UserAuthenticationFailException;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.ui.SystemRoleResolver;
import org.qi4j.composite.scope.Service;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWorkFactory;

/**
 * TODO: Refactor this
 *
 * @author Lan Boon Ping
 * @author edward.yakop@gmail.com
 */
public final class ChronosSession extends AuthenticatedWebSession
{
    private static final long serialVersionUID = 1L;

    private @Structure UnitOfWorkFactory unitOfWorkFactory;

    private @Service UserService userService;

    private SystemRoleResolver roleResolver;

    private String userId;

    private Account account;

    private User user;

    public ChronosSession( @Uses Request request )
    {
        super( request );

        userId = null;
    }

    public static ChronosSession get()
    {
        return (ChronosSession) Session.get();
    }

    public final boolean authenticate( String aUserName, String aPassword )
    {
        if( aUserName == null || aPassword == null )
        {
            return false;
        }

        try
        {
            User user = userService.authenticate( account, aUserName, aPassword );

            this.user = user;
            this.userId = user.identity().get();

            this.roleResolver = new SystemRoleResolver( user );

            return true;
        }
        catch( UserAuthenticationFailException e )
        {
            return false;

        }
    }

    public UnitOfWorkFactory getUnitOfWorkFactory()
    {
        return unitOfWorkFactory;
    }

    public SystemRoleResolver getSystemRoleResolver()
    {
        return roleResolver;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setAccount( Account anAccount )
    {
        account = anAccount;
    }

    public boolean isStaff()
    {
        return getUser() instanceof Staff;
    }

    public Account getAccount()
    {
        return this.account;
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