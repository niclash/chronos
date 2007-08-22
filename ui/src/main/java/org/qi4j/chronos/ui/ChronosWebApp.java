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

import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.composites.ServicesComposite;
import org.qi4j.chronos.service.mocks.MockServicesMixin;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.login.LoginPage;
import org.qi4j.runtime.CompositeBuilderFactoryImpl;

public class ChronosWebApp extends AuthenticatedWebApplication
{
    private static CompositeBuilderFactory factory;
    private static Services services;

    static
    {
        factory = new CompositeBuilderFactoryImpl();

        CompositeBuilder<ServicesComposite> serviceBuilder = factory.newCompositeBuilder( ServicesComposite.class );

        //TODO bp. use mock services for now.
        serviceBuilder.setMixin( Services.class, new MockServicesMixin( factory ) );

        services = serviceBuilder.newInstance();
    }

    public static Services getServices()
    {
        return services;
    }

    public static <T extends Composite> CompositeBuilder<T> newCompositeBuilder( Class<T> compositeType )
    {
        return factory.newCompositeBuilder( compositeType );
    }

    public static <T extends Composite> T newInstance( Class<T> compositeType )
    {
        return factory.newCompositeBuilder( compositeType ).newInstance();
    }

    public Class getHomePage()
    {
        ChronosSession chronosSession = (ChronosSession) Session.get();

        if( !chronosSession.isSignIn() )
        {
            return LoginPage.class;
        }
        else
        {
            //TODO bp. direct the user to right home page. e.g StaffHomePage, CustomerHomePage or AdminHomePage
            return AdminHomePage.class;
        }
    }

    protected Class<? extends AuthenticatedWebSession> getWebSessionClass()
    {
        return ChronosSession.class;
    }

    protected Class<? extends WebPage> getSignInPageClass()
    {
        return LoginPage.class;
    }

}
