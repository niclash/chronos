/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Edward Yakop. All Rights Reserved.
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
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.settings.IApplicationSettings;
import org.apache.wicket.settings.ISessionSettings;
import org.qi4j.annotation.scope.Structure;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;

/**
 * @author Lan Boon Ping
 * @author edward.yakop@gmail.com
 */
final class ChronosWebApp extends AuthenticatedWebApplication
{
    private ObjectBuilderFactory objectBuilderFactory;

    public ChronosWebApp( @Structure ObjectBuilderFactory anObjectBuilderFactory )
        throws IllegalArgumentException
    {
        super();

        validateNotNull( "anObjectBuilderFactory", anObjectBuilderFactory );
        objectBuilderFactory = anObjectBuilderFactory;
    }

    @Override
    protected void init()
    {
        super.init();

        //override page expired page to LoginPage
        IApplicationSettings applicationSettings = getApplicationSettings();
        applicationSettings.setPageExpiredErrorPage( LoginPage.class );

        // Set page factory
        ISessionSettings sessionSettings = getSessionSettings();
        ObjectBuilder<ChronosPageFactory> builder = objectBuilderFactory.newObjectBuilder( ChronosPageFactory.class );
        ChronosPageFactory pageFactory = builder.newInstance();
        sessionSettings.setPageFactory( pageFactory );
    }

    protected Class<? extends AuthenticatedWebSession> getWebSessionClass()
    {
        return ChronosSession.class;
    }

    @Override
    public final Session newSession( Request request, Response response )
    {
        // TODO: Ideally what we want to do is to pass the request and reponse as parameter value
        return super.newSession( request, response );
    }

    protected Class<? extends WebPage> getSignInPageClass()
    {
        return LoginPage.class;
    }

    public Class getHomePage()
    {
        return LoginPage.class;
    }
}