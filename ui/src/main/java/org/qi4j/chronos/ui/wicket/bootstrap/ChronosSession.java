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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.SystemRoleResolver;
import org.qi4j.composite.scope.PropertyParameter;
import org.qi4j.composite.scope.Service;

/**
 * TODO: Refactor this
 *
 * @author Lan Boon Ping
 * @author edward.yakop@gmail.com
 */
public final class ChronosSession extends AuthenticatedWebSession
{
    private static final long serialVersionUID = 1L;

    static final String PARAMETER_AUTHENTICATED_WEB_APPLICATION = "authenticatedWebApplication";
    static final String PARAMETER_REQUEST = "request";

    @Service
    private AuthenticationService authenticationService;

    @Service
    private AccountService accountService;


    private SystemRoleResolver roleResolver;
    private String userId;
    private String accountId;

    public ChronosSession(
        @PropertyParameter( PARAMETER_AUTHENTICATED_WEB_APPLICATION )AuthenticatedWebApplication app,
        @PropertyParameter( PARAMETER_REQUEST )Request request )
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
        if( aUserName == null )
        {
            return false;
        }
        return authenticationService.authenticate( aUserName, aPassword );

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