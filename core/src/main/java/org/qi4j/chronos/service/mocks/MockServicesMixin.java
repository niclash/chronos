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
package org.qi4j.chronos.service.mocks;

import java.util.Currency;
import java.util.List;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.service.associations.HasPriceRateScheduleService;
import org.qi4j.chronos.service.composites.AccountServiceComposite;
import org.qi4j.chronos.service.composites.LegalConditionServiceComposite;
import org.qi4j.chronos.service.composites.ProjectOwnerServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RoleServiceComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.library.general.model.GenderType;

public class MockServicesMixin implements Services
{
    private CompositeBuilderFactory factory;

    private AccountService accountService;
    private ProjectService projectService;
    private ProjectRoleService projectRoleService;
    private StaffService staffService;
    private UserService userService;
    private SystemRoleService systemRoleService;
    private LegalConditionService legalConditionService;
    private ProjectOwnerService projectOwnerService;

    public MockServicesMixin( CompositeBuilderFactory factory )
    {
        this.factory = factory;

        accountService = initAccountService();

        projectService = newService( ProjectServiceComposite.class );
        projectRoleService = newService( RoleServiceComposite.class );
        staffService = newService( StaffServiceComposite.class );
        userService = initUserService( staffService );
        systemRoleService = newService( SystemRoleServiceComposite.class );
        legalConditionService = newService( LegalConditionServiceComposite.class );
        projectOwnerService = newService( ProjectOwnerServiceComposite.class );

        initDummyData();
    }

    private AccountService initAccountService()
    {
        CompositeBuilder<AccountServiceComposite> compositeBuilder = factory.newCompositeBuilder( AccountServiceComposite.class );

        compositeBuilder.setMixin( EntityService.class, new MockEntityServiceMixin( factory ) );

        compositeBuilder.setMixin( HasPriceRateScheduleService.class, new MockHasPriceRateScheduleServiceMixin() );

        return compositeBuilder.newInstance();
    }

    private UserService initUserService( StaffService staffService )
    {
        CompositeBuilder<UserServiceComposite> compositeBuilder = factory.newCompositeBuilder( UserServiceComposite.class );

        compositeBuilder.setMixin( UserService.class, new MockUserServiceMixin( staffService ) );

        return compositeBuilder.newInstance();
    }

    private void initDummyData()
    {
        initAccountDummyData();
        initProjectRoleDummyData();
        initSystemRoleDummyData();
        initStaffDummyData();
    }

    private void initAccountDummyData()
    {
        for( int i = 0; i < 50; i++ )
        {
            AccountEntityComposite account = accountService.newInstance( AccountEntityComposite.class );

            account.setName( "accountName " + i );

            accountService.save( account );
        }
    }

    private void initProjectRoleDummyData()
    {
        ProjectRoleEntityComposite programmer = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        programmer.setRole( "Programmer" );

        ProjectRoleEntityComposite consultant = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        consultant.setRole( "Consultant" );

        ProjectRoleEntityComposite projectManager = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        projectManager.setRole( "Project Manager" );

        projectRoleService.save( programmer );
        projectRoleService.save( consultant );
        projectRoleService.save( projectManager );
    }

    private void initStaffDummyData()
    {
        StaffEntityComposite staff = staffService.newInstance( StaffEntityComposite.class );

        staff.setFirstName( "admin" );
        staff.setLastName( "admin" );
        staff.setGender( GenderType.male );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "admin" );
        login.setPassword( "admin" );
        login.setEnabled( true );

        staff.setLogin( login );

        MoneyComposite money = factory.newCompositeBuilder( MoneyComposite.class ).newInstance();

        money.setAmount( 0L );
        money.setCurrency( Currency.getInstance( "USD" ) );

        staff.setSalary( money );

        List<SystemRoleEntityComposite> systemRoleList = systemRoleService.findAll();

        for( SystemRoleEntityComposite systemRole : systemRoleList )
        {
            staff.addSystemRole( systemRole );
        }

        staffService.save( staff );
    }

    private void initSystemRoleDummyData()
    {
        SystemRoleEntityComposite admin = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        admin.setName( "Administrator" );

        SystemRoleEntityComposite staff = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        staff.setName( "Staff" );

        SystemRoleEntityComposite customer = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        customer.setName( "Customer" );

        systemRoleService.save( admin );
        systemRoleService.save( staff );
        systemRoleService.save( customer );
    }

    public AccountService getAccountService()
    {
        return accountService;
    }

    public ProjectService getProjectService()
    {
        return projectService;
    }

    public ProjectRoleService getProjectRoleService()
    {
        return projectRoleService;
    }

    public StaffService getStaffService()
    {
        return staffService;
    }

    public UserService getUserService()
    {
        return userService;
    }

    public SystemRoleService getSystemRoleService()
    {
        return systemRoleService;
    }

    public LegalConditionService getLegalConditionService()
    {
        return legalConditionService;
    }

    public ProjectOwnerService getProjectOwnerService()
    {
        return projectOwnerService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.setMixin( EntityService.class, new MockEntityServiceMixin( factory ) );

        return (T) compositeBuilder.newInstance();
    }
}
