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
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ContactTypeComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.NameWithReferenceComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.composites.AccountServiceComposite;
import org.qi4j.chronos.service.composites.AdminServiceComposite;
import org.qi4j.chronos.service.composites.CommentServiceComposite;
import org.qi4j.chronos.service.composites.ContactPersonServiceComposite;
import org.qi4j.chronos.service.composites.ContactServiceComposite;
import org.qi4j.chronos.service.composites.CustomerServiceComposite;
import org.qi4j.chronos.service.composites.LegalConditionServiceComposite;
import org.qi4j.chronos.service.composites.OngoingWorkEntryServiceComposite;
import org.qi4j.chronos.service.composites.PriceRateScheduleServiceComposite;
import org.qi4j.chronos.service.composites.PriceRateServiceComposite;
import org.qi4j.chronos.service.composites.ProjectAssigneeServiceComposite;
import org.qi4j.chronos.service.composites.ProjectRoleServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RelationshipServiceComposite;
import org.qi4j.chronos.service.composites.ServicesComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.TaskServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.chronos.service.composites.WorkEntryServiceComposite;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.contactperson.ContactPersonHomePage;
import org.qi4j.chronos.ui.login.LoginPage;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;

public class ChronosWebApp extends AuthenticatedWebApplication
{
    private static CompositeBuilderFactory factory;

    private static UnitOfWorkFactory uowFactory;

    private static Services services;

    static
    {
        SingletonAssembler assembly = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.addComposites(
                    CityComposite.class,
                    StateComposite.class,
                    CountryComposite.class,
                    AddressComposite.class,
                    ContactComposite.class,
                    ContactTypeComposite.class,
                    LegalConditionComposite.class,
                    SystemRoleComposite.class,
                    PriceRateComposite.class,
                    MoneyComposite.class,
                    CommentComposite.class,
                    AccountEntityComposite.class,
                    AdminEntityComposite.class,
                    ContactPersonEntityComposite.class,
                    CustomerEntityComposite.class,
                    LoginComposite.class,
                    NameWithReferenceComposite.class,
                    OngoingWorkEntryEntityComposite.class,
                    PriceRateScheduleComposite.class,
                    ProjectAssigneeEntityComposite.class,
                    ProjectEntityComposite.class,
                    ProjectRoleComposite.class,
                    RelationshipComposite.class,
                    StaffEntityComposite.class,
                    SystemEntityComposite.class,
                    TaskEntityComposite.class,
                    TimeRangeEntityComposite.class,
                    WorkEntryEntityComposite.class,
                    CommentServiceComposite.class,
                    ContactPersonServiceComposite.class,
                    AccountServiceComposite.class,
                    AdminServiceComposite.class,
                    ContactServiceComposite.class,
                    CustomerServiceComposite.class,
                    LegalConditionServiceComposite.class,
                    OngoingWorkEntryServiceComposite.class,
                    PriceRateScheduleServiceComposite.class,
                    PriceRateServiceComposite.class,
                    ProjectAssigneeServiceComposite.class,
                    ProjectRoleServiceComposite.class,
                    ProjectServiceComposite.class,
                    RelationshipServiceComposite.class,
                    StaffServiceComposite.class,
                    SystemRoleServiceComposite.class,
                    TaskServiceComposite.class,
                    UserServiceComposite.class,
                    WorkEntryServiceComposite.class
                );
                module.addServices(
                    ServicesComposite.class,
                    MemoryEntityStoreService.class,
                    UuidIdentityGeneratorService.class
                );
            }
        };
        factory = assembly.getCompositeBuilderFactory();
        uowFactory = assembly.getUnitOfWorkFactory();

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

    public static <T extends EntityComposite> T newEntityInstance( Class<T> compositeType )
    {
        UnitOfWork uow = uowFactory.newUnitOfWork();
        return uow.newEntityBuilder( compositeType ).newInstance();
    }

    public static UnitOfWork newUnitOfWork()
    {
        return uowFactory.newUnitOfWork();
    }

    public static UnitOfWork currentUnitOfWork()
    {
        return uowFactory.currentUnitOfWork();
    }

    public static <T extends EntityComposite> T dereference( T obj )
    {
        return uowFactory.newUnitOfWork().dereference( obj );
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
