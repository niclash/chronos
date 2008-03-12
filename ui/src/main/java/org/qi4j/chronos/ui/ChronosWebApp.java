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

import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.qi4j.bootstrap.AssemblerException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.composites.ServicesComposite;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.contactperson.ContactPersonHomePage;
import org.qi4j.chronos.ui.login.LoginPage;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;

public class ChronosWebApp extends AuthenticatedWebApplication
{
    private static CompositeBuilderFactory factory;

    private static Services services;

    static
    {
        SingletonAssembler assembly = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module ) throws AssemblerException
            {
                module.addComposites( ServicesComposite.class );
            }
        };
        factory = assembly.getCompositeBuilderFactory();

        CompositeBuilder<ServicesComposite> serviceBuilder = factory.newCompositeBuilder( ServicesComposite.class );

        services = serviceBuilder.newInstance();

        services.initServices();
    }

    @Override
    protected void init()
    {
        super.init();

        //override page expired page to LoginPage
        getApplicationSettings().setPageExpiredErrorPage( LoginPage.class );
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
        ChronosSession chronosSession = ChronosSession.get();

        SystemRoleResolver resolver = chronosSession.getSystemRoleResolver();

        if( !chronosSession.isSignIn() )
        {
            return LoginPage.class;
        }
        else
        {
            if( resolver.isAdmin() )
            {
                return AdminHomePage.class;
            }
            else if( resolver.isStaff() )
            {
                return StaffHomePage.class;
            }
            else if( resolver.isContactPerson() )
            {
                return ContactPersonHomePage.class;
            }
            else
            {
                throw new IllegalArgumentException( "Unhandle user type " +
                                                    chronosSession.getUser().getClass().getName() );
            }
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
