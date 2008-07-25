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
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.service.UserAuthenticationFailException;
import org.qi4j.chronos.service.UserService;
import org.qi4j.composite.Composite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.injection.scope.Service;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.Uses;
import org.qi4j.service.ServiceFinder;
import org.qi4j.spi.Qi4jSPI;
import org.qi4j.spi.composite.CompositeDescriptor;

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
    private @Structure UnitOfWorkFactory unitOfWorkFactory;

    private @Structure ServiceFinder serviceFinder;

    private @Structure Qi4jSPI spi;

    private String userId;
    private Class<? extends Composite> userType;
    private String accountId;

    private transient Account account;
    private transient User user;
    private transient Roles roles;

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
            user = userService.authenticate( account, aUserName, aPassword );
            CompositeDescriptor descriptor = spi.getCompositeDescriptor( (Composite) user );
            userType = descriptor.type();
            userId = user.identity().get();
            return true;
        }
        catch( UserAuthenticationFailException e )
        {
            return false;
        }
    }

    public final User getUser()
    {
        if( userId != null && user == null )
        {
            UnitOfWork work = unitOfWorkFactory.currentUnitOfWork();
            user = (User) work.getReference( userId, userType );
        }

        return user;
    }

    public final void setAccount( Account anAccount )
    {
        if( anAccount != null )
        {
            accountId = anAccount.identity().get();
        }
        else
        {
            accountId = null;
        }

        account = anAccount;
    }

    public Account getAccount()
    {
        if( accountId != null && account == null )
        {
            UnitOfWork work = unitOfWorkFactory.currentUnitOfWork();
            account = work.getReference( accountId, Account.class );
        }

        return account;
    }

    public boolean isSignIn()
    {
        return userId != null;
    }

    @Override
    public final Roles getRoles()
    {
        if( roles != null )
        {
            return roles;
        }

        roles = new Roles();
        User user = getUser();
        if( user != null )
        {

            SetAssociation<SystemRole> systemRoles = user.systemRoles();
            for( SystemRole systemRole : systemRoles )
            {
                String systemRoleName = systemRole.name().get();
                roles.add( systemRoleName );
            }
        }
        return roles;
    }

    @Override
    protected final void attach()
    {
        ChronosUnitOfWorkManager.set( unitOfWorkManager );

        roles = null;
        user = null;
        account = null;

        // TODO
        ChronosServiceFinderHelper helper = new ChronosServiceFinderHelper( serviceFinder );
        ChronosServiceFinderHelper.set( helper );

        super.attach();
    }

    @Override
    protected final void detach()
    {
        ChronosUnitOfWorkManager.set( null );
        ChronosServiceFinderHelper.set( null );

        roles = null;
        user = null;
        account = null;

        super.detach();
    }
}