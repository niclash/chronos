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
package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.service.systemrole.SystemRoleService;
import org.qi4j.chronos.service.AggregatedService;
import org.qi4j.chronos.ui.SystemRoleResolver;
import org.qi4j.composite.scope.Service;
import org.qi4j.composite.scope.Uses;
import org.qi4j.composite.scope.Structure;
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

    private @Service AuthenticationService authenticationService;

    private @Service AccountService accountService;

    private @Service UserService userService;

    private @Service AggregatedService aggregatedService;

    private @Service SystemRoleService systemRoleService;

    private @Structure UnitOfWorkFactory factory;

    private SystemRoleResolver roleResolver;

    private String userId;

    private String accountId;

    private Account account;

    private User user;

    public ChronosSession(
        @Uses AuthenticatedWebApplication app,
        @Uses Request request )
    {
        super( app, request );

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

        User user = authenticationService.authenticate( accountId, aUserName, aPassword );

        if( user != null )
        {
            this.user = user;
            this.userId = user.identity().get();
            this.roleResolver = new SystemRoleResolver( user );

            if( !( user instanceof Admin ) )
            {
                this.account = accountService.get( accountId );
            }

            return true;
        }

        return false;
    }

    public User getUser()
    {
        return this.user;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId( String accountId )
    {
        this.accountId = accountId;
    }

    public boolean isStaff()
    {
        return getUser() instanceof Staff;
    }

    public Account getAccount()
    {
        return this.account;
    }

    public AccountService getAccountService()
    {
        return accountService;
    }

    public UserService getUserService()
    {
        return userService;
    }

    public SystemRoleResolver getSystemRoleResolver()
    {
        return roleResolver;
    }

    public SystemRoleService getSystemRoleService()
    {
        return systemRoleService;
    }

    public UnitOfWorkFactory getUnitOfWorkFactory()
    {
        return this.factory;
    }

    public AggregatedService getService()
    {
        return this.aggregatedService;
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