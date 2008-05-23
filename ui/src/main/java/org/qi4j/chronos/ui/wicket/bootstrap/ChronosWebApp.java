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
import static org.apache.wicket.util.lang.Objects.setObjectStreamFactory;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.contactperson.ContactPersonHomePage;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import org.qi4j.chronos.ui.wicket.bootstrap.serialization.Qi4jObjectStreamFactory;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;
import org.qi4j.composite.scope.Structure;

/**
 * @author Lan Boon Ping
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class ChronosWebApp extends AuthenticatedWebApplication
{
    @Structure
    private ObjectBuilderFactory objectBuilderFactory;

    @Override
    protected final void init()
    {
        super.init();

        //override page expired page to LoginPage
        IApplicationSettings applicationSettings = getApplicationSettings();
        applicationSettings.setPageExpiredErrorPage( LoginPage.class );

        // Set page factory
        ISessionSettings sessionSettings = getSessionSettings();
        ObjectBuilder<ChronosPageFactory> pageFactoryBuilder
            = objectBuilderFactory.newObjectBuilder( ChronosPageFactory.class );
        ChronosPageFactory pageFactory = pageFactoryBuilder.newInstance();
        sessionSettings.setPageFactory( pageFactory );

        // Sets the object stream factory builder
        ObjectBuilder<Qi4jObjectStreamFactory> objectStreamFactoryBuilder
            = objectBuilderFactory.newObjectBuilder( Qi4jObjectStreamFactory.class );
        Qi4jObjectStreamFactory objectStreamFactory = objectStreamFactoryBuilder.newInstance();
        setObjectStreamFactory( objectStreamFactory );
    }

    @Override
    protected Class<? extends AuthenticatedWebSession> getWebSessionClass()
    {
        return ChronosSession.class;
    }

    /**
     * Instantiate chronos session.
     *
     * @param request  The request.
     * @param response The response.
     * @return A new instance of chronos session.
     * @since 0.1.0
     */
    @Override
    public final Session newSession( Request request, Response response )
    {
        ObjectBuilder<ChronosSession> builder = objectBuilderFactory.newObjectBuilder( ChronosSession.class );
        builder.use( request, response );
        return builder.newInstance();
    }

    /**
     * Returns the sign in page.
     *
     * @return The sign in page.
     */
    @Override
    protected final Class<? extends WebPage> getSignInPageClass()
    {
        return LoginPage.class;
    }

    @Override
    public final Class<? extends WebPage> getHomePage()
    {
        ChronosSession session = ChronosSession.get();
        
        if( !session.isSignIn() )
        {
            return LoginPage.class;
        }
        else
        {
            User user = session.getUser();

            if( user instanceof Admin )
            {
                return AdminHomePage.class;
            }
            else if( user instanceof Staff )
            {
                return StaffHomePage.class;
            }
            else
            {
                return ContactPersonHomePage.class;
            }
        }
    }
}