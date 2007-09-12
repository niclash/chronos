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
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.AccountServiceMisc;
import org.qi4j.chronos.service.AdminService;
import org.qi4j.chronos.service.BasedService;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.service.SystemRoleServiceMisc;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.service.composites.AccountServiceComposite;
import org.qi4j.chronos.service.composites.AdminServiceComposite;
import org.qi4j.chronos.service.composites.ContactPersonServiceComposite;
import org.qi4j.chronos.service.composites.ProjectOwnerServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RoleServiceComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public class MockServicesMixin implements Services
{
    private CompositeBuilderFactory factory;

    private AccountService accountService;
    private ProjectService projectService;
    private ProjectRoleService projectRoleService;
    private StaffService staffService;
    private UserService userService;
    private SystemRoleService systemRoleService;
    private ProjectOwnerService projectOwnerService;
    private AdminService adminService;
    private ContactPersonService contactPersonService;

    public MockServicesMixin( CompositeBuilderFactory factory )
    {
        this.factory = factory;

        accountService = initAccountService();
        projectService = initProjectService();

        projectRoleService = newService( RoleServiceComposite.class );
        adminService = newService( AdminServiceComposite.class );
        staffService = initStaffService( accountService );

        systemRoleService = initSystemRoleService();
        projectOwnerService = initProjectOwnerService();
        contactPersonService = initContactPersonService();

        userService = initUserService( staffService, adminService, contactPersonService );
        initDummyData();
    }

    private ContactPersonService initContactPersonService()
    {
        CompositeBuilder<ContactPersonServiceComposite> compositeBuilder = factory.newCompositeBuilder( ContactPersonServiceComposite.class );

        compositeBuilder.setMixin( BasedService.class, new MockContactPersonServiceMixin( factory, projectOwnerService ) );

        return compositeBuilder.newInstance();
    }

    private ProjectService initProjectService()
    {
        CompositeBuilder<ProjectServiceComposite> compositeBuilder = factory.newCompositeBuilder( ProjectServiceComposite.class );

        compositeBuilder.setMixin( BasedService.class, new MockProjectServiceMixin( factory, accountService ) );

        return compositeBuilder.newInstance();
    }

    private ProjectOwnerService initProjectOwnerService()
    {
        CompositeBuilder<ProjectOwnerServiceComposite> compositeBuilder = factory.newCompositeBuilder( ProjectOwnerServiceComposite.class );

        compositeBuilder.setMixin( BasedService.class, new MockProjectOwnerServiceMixin( factory, accountService ) );

        return compositeBuilder.newInstance();
    }

    private AccountServiceComposite initAccountService()
    {
        CompositeBuilder<AccountServiceComposite> compositeBuilder = factory.newCompositeBuilder( AccountServiceComposite.class );

        MockEntityServiceMixin entityServiceMixin = new MockEntityServiceMixin( factory );

        compositeBuilder.setMixin( EntityService.class, entityServiceMixin );
        compositeBuilder.setMixin( AccountServiceMisc.class, new MockAccountServiceMiscMixin( entityServiceMixin ) );

        return compositeBuilder.newInstance();
    }

    private StaffServiceComposite initStaffService( AccountService accountService )
    {
        CompositeBuilder<StaffServiceComposite> compositeBuilder = factory.newCompositeBuilder( StaffServiceComposite.class );

        compositeBuilder.setMixin( BasedService.class, new MockStaffServiceMixin( factory, accountService ) );

        return compositeBuilder.newInstance();
    }

    private SystemRoleService initSystemRoleService()
    {
        CompositeBuilder<SystemRoleServiceComposite> compositeBuilder = factory.newCompositeBuilder( SystemRoleServiceComposite.class );

        MockEntityServiceMixin serviceMixin = new MockEntityServiceMixin( factory );

        compositeBuilder.setMixin( EntityService.class, serviceMixin );
        compositeBuilder.setMixin( SystemRoleServiceMisc.class, new MockSystemRoleServiceMiscMixin( serviceMixin ) );

        return compositeBuilder.newInstance();
    }

    private UserService initUserService( StaffService staffService, AdminService adminService, ContactPersonService contactPersonService )
    {
        CompositeBuilder<UserServiceComposite> compositeBuilder = factory.newCompositeBuilder( UserServiceComposite.class );

        compositeBuilder.setMixin( UserService.class, new MockUserServiceMixin( staffService, adminService, contactPersonService ) );

        return compositeBuilder.newInstance();
    }

    private void initDummyData()
    {
        AccountEntityComposite account = initAccountDummyData();

        initProjectRoleDummyData();
        initSystemRoleDummyData();
        initStaffDummyData( account );
        initAdminDummyData();
        initProjectOwnerDummyData( account );
    }

    private AccountEntityComposite initAccountDummyData()
    {
        AccountEntityComposite account = accountService.newInstance( AccountEntityComposite.class );

        account.setName( "Jayway Malaysia" );
        account.setReference( "Jayway Malaysia Sdn Bhd" );
        account.setEnabled( true );

        AddressComposite address = factory.newCompositeBuilder( AddressComposite.class ).newInstance();
        CityComposite city = factory.newCompositeBuilder( CityComposite.class ).newInstance();
        StateComposite state = factory.newCompositeBuilder( StateComposite.class ).newInstance();
        CountryComposite country = factory.newCompositeBuilder( CountryComposite.class ).newInstance();

        address.setCity( city );

        city.setState( state );
        city.setCountry( country );

        account.setAddress( address );

        address.getCity().setName( "city1" );
        address.getCity().getCountry().setName( "Malaysia" );
        address.getCity().getState().setName( "KL" );

        address.setFirstLine( "ABC Road" );
        address.setSecondLine( "Way Center" );
        address.setZipCode( "999" );

        accountService.save( account );

        return account;
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

    private void initAdminDummyData()
    {
        AdminEntityComposite admin = adminService.newInstance( AdminEntityComposite.class );

        admin.setFirstName( "admin" );
        admin.setLastName( "admin" );
        admin.setGender( GenderType.male );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "admin" );
        login.setPassword( "admin" );
        login.setEnabled( true );

        admin.setLogin( login );

        List<SystemRoleEntityComposite> systemRoleList = systemRoleService.findAll();

        for( SystemRoleEntityComposite systemRole : systemRoleList )
        {
            admin.addSystemRole( systemRole );
        }

        adminService.save( admin );
    }

    private void initStaffDummyData( AccountEntityComposite account )
    {
        StaffEntityComposite staff = staffService.newInstance( StaffEntityComposite.class );

        staff.setFirstName( "user" );
        staff.setLastName( "user" );
        staff.setGender( GenderType.male );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "user" );
        login.setPassword( "user" );
        login.setEnabled( true );

        staff.setLogin( login );

        MoneyComposite money = factory.newCompositeBuilder( MoneyComposite.class ).newInstance();

        money.setAmount( 0L );
        money.setCurrency( Currency.getInstance( "USD" ) );

        staff.setSalary( money );

        List<SystemRoleEntityComposite> systemRoleList = systemRoleService.findAllStaffSystemRole();

        for( SystemRoleEntityComposite systemRole : systemRoleList )
        {
            staff.addSystemRole( systemRole );
        }

        account.addStaff( staff );
    }


    private void initSystemRoleDummyData()
    {
        SystemRoleEntityComposite admin = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        admin.setName( SystemRole.SYSTEM_ADMIN );
        admin.setSystemRoleType( SystemRoleType.ADMIN );

        SystemRoleEntityComposite accountAdmin = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        accountAdmin.setName( SystemRole.ACCOUNT_ADMIN );
        accountAdmin.setSystemRoleType( SystemRoleType.STAFF );

        SystemRoleEntityComposite developer = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        developer.setName( SystemRole.ACCOUNT_DEVELOPER );
        developer.setSystemRoleType( SystemRoleType.STAFF );

        SystemRoleEntityComposite projectManager = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        projectManager.setName( SystemRole.ACCOUNT_PROJECT_MANAGER );
        projectManager.setSystemRoleType( SystemRoleType.STAFF );

        SystemRoleEntityComposite contactPerson = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        contactPerson.setName( SystemRole.CONTACT_PERSON );
        contactPerson.setSystemRoleType( SystemRoleType.CONTACT_PERSON );

        systemRoleService.save( admin );
        systemRoleService.save( accountAdmin );
        systemRoleService.save( developer );
        systemRoleService.save( projectManager );
        systemRoleService.save( contactPerson );
    }

    private void initProjectOwnerDummyData( AccountEntityComposite account )
    {
        ProjectOwnerEntityComposite projectOwner = projectOwnerService.newInstance( ProjectOwnerEntityComposite.class );

        projectOwner.setName( "ABC" );
        projectOwner.setReference( "ABC Ltd" );

        AddressComposite address = factory.newCompositeBuilder( AddressComposite.class ).newInstance();
        CityComposite city = factory.newCompositeBuilder( CityComposite.class ).newInstance();
        StateComposite state = factory.newCompositeBuilder( StateComposite.class ).newInstance();
        CountryComposite country = factory.newCompositeBuilder( CountryComposite.class ).newInstance();

        address.setCity( city );

        city.setState( state );
        city.setCountry( country );

        projectOwner.setAddress( address );

        address.getCity().setName( "City" );
        address.getCity().getCountry().setName( "Sweden" );
        address.getCity().getState().setName( "KL" );

        address.setFirstLine( "Golden Road" );
        address.setSecondLine( "Uber City" );
        address.setZipCode( "123" );

        account.addProjectOwner( projectOwner );
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

    public ProjectOwnerService getProjectOwnerService()
    {
        return projectOwnerService;
    }

    public AdminService getAdminService()
    {
        return adminService;
    }

    public ContactPersonService getContactPersonService()
    {
        return contactPersonService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.setMixin( EntityService.class, new MockEntityServiceMixin( factory ) );

        return (T) compositeBuilder.newInstance();
    }
}
