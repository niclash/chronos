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
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.injection.scope.Service;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.Uses;
import org.qi4j.service.ServiceFinder;

/**
 * TODO: Refactor this
 *
 * @author Lan Boon Ping
 * @author edward.yakop@gmail.com
 */
public final class ChronosSession extends AuthenticatedWebSession
{
    private static final long serialVersionUID = 1L;

    private @Service UserService userService;

    private @Structure ServiceFinder serviceFinder;

    private SystemRoleResolver roleResolver;

    private String userId;
    private Account account;
    private User user;
    private ChronosUnitOfWorkManager unitOfWorkManager;

    public ChronosSession( @Uses Request request, @Structure UnitOfWorkFactory aUOWF )
    {
        super( request );

        unitOfWorkManager = new ChronosUnitOfWorkManager( aUOWF );
        userId = null;
    }

    public static ChronosSession get()
    {
        return (ChronosSession) Session.get();
    }

    @Override
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

    public SystemRoleResolver getSystemRoleResolver()
    {
        return roleResolver;
    }

    public User getUser()
    {
        if( user != null )
        {
            UnitOfWork work = unitOfWorkManager.getCurrentUnitOfWork();
            return work.dereference( user );
        }

        return null;
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
        if( account != null )
        {
            UnitOfWork work = unitOfWorkManager.getCurrentUnitOfWork();
            return work.dereference( account );
        }

        return null;
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    @Override
    public final Roles getRoles()
    {
        return roleResolver.getRoles();
    }

    @Override
    protected final void attach()
    {
        ChronosUnitOfWorkManager.set( unitOfWorkManager );

        ChronosServiceFinderHelper helper = new ChronosServiceFinderHelper( serviceFinder );

        ChronosServiceFinderHelper.set( helper );

        super.attach();
    }

    @Override
    protected final void detach()
    {
        ChronosUnitOfWorkManager.set( null );

        ChronosServiceFinderHelper.set( null );

        super.detach();
    }
}