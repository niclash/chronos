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
import org.qi4j.api.PropertyValue;
import org.qi4j.api.annotation.scope.Fragment;
import org.qi4j.chronos.model.ProjectOwner;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.AdminService;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.ParentBasedService;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.service.composites.AccountServiceComposite;
import org.qi4j.chronos.service.composites.AdminServiceComposite;
import org.qi4j.chronos.service.composites.ContactPersonServiceComposite;
import org.qi4j.chronos.service.composites.PriceRateScheduleServiceComposite;
import org.qi4j.chronos.service.composites.ProjectAssigneeServiceComposite;
import org.qi4j.chronos.service.composites.ProjectOwnerServiceComposite;
import org.qi4j.chronos.service.composites.ProjectRoleServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RelationshipServiceComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.chronos.service.composites.WorkEntryServiceComposite;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public class MockServicesMixin implements Services
{
    @Fragment private CompositeBuilderFactory factory;

    private AccountService accountService;
    private ProjectService projectService;
    private ProjectRoleService projectRoleService;
    private StaffService staffService;
    private UserService userService;
    private SystemRoleService systemRoleService;
    private ProjectOwnerService projectOwnerService;
    private AdminService adminService;
    private ContactPersonService contactPersonService;
    private PriceRateScheduleService priceRateScheduleService;
    private ProjectAssigneeService projectAssigneeService;
    private WorkEntryService workEntryService;
    private RelationshipService relationshipService;

    public void initServices()
    {
        accountService = newService( AccountServiceComposite.class );
        projectService = newParentBasedService( ProjectServiceComposite.class, "accountService", accountService );

        projectRoleService = newService( ProjectRoleServiceComposite.class );
        adminService = newService( AdminServiceComposite.class );
        staffService = newParentBasedService( StaffServiceComposite.class, "accountService ", accountService );

        systemRoleService = newService( SystemRoleServiceComposite.class );
        projectOwnerService = newParentBasedService( ProjectOwnerServiceComposite.class, "accountService", accountService );
        contactPersonService = newParentBasedService( ContactPersonServiceComposite.class, "projectOwnerService", projectOwnerService );

        userService = initUserService( staffService, adminService, contactPersonService );

        priceRateScheduleService = newService( PriceRateScheduleServiceComposite.class );

        projectAssigneeService = newParentBasedService( ProjectAssigneeServiceComposite.class, "projectService", projectService );

        workEntryService = newParentBasedService( WorkEntryServiceComposite.class, "projectAssigneeService", projectAssigneeService );

        relationshipService = newService( RelationshipServiceComposite.class );

        initDummyData();
    }

    private <T extends Composite> T newParentBasedService( Class<T> clazz, String propertyName, Object propertyValue )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.properties( ParentBasedService.class, PropertyValue.property( propertyName, propertyValue ) );

        return (T) compositeBuilder.newInstance();
    }

    private UserService initUserService( StaffService staffService, AdminService adminService, ContactPersonService contactPersonService )
    {
        CompositeBuilder<UserServiceComposite> compositeBuilder = factory.newCompositeBuilder( UserServiceComposite.class );

        compositeBuilder.properties( UserService.class,
                                     PropertyValue.property( "staffService", staffService ),
                                     PropertyValue.property( "adminService", adminService ),
                                     PropertyValue.property( "contactPersonService", contactPersonService ) );

        return compositeBuilder.newInstance();
    }

    private void initDummyData()
    {
        AccountEntityComposite account = initAccountDummyData();

        initProjectRoleDummyData();
        initSystemRoleDummyData();
        initStaffDummyData( account );
//        initAdminDummyData();
//
//        ProjectOwner projectOwner = initProjectOwnerDummyData( account );
//
//        initContactPerson( projectOwner );
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

        systemRoleService.findAll();
    }

    private ProjectOwner initProjectOwnerDummyData( AccountEntityComposite account )
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

        return projectOwner;
    }

    private void initContactPerson( ProjectOwner projectOwner )
    {
        SystemRoleEntityComposite contactPersonRole = systemRoleService.getSystemRoleByName( SystemRole.CONTACT_PERSON );

        ContactPersonEntityComposite contactPerson = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson.setFirstName( "michael" );
        contactPerson.setLastName( "michael" );
        contactPerson.setGender( GenderType.male );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "michael" );
        login.setPassword( "michael" );
        login.setEnabled( true );

        contactPerson.setLogin( login );
        contactPerson.addSystemRole( contactPersonRole );

        ContactPersonEntityComposite contactPerson2 = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson2.setFirstName( "mimi" );
        contactPerson2.setLastName( "mimi" );
        contactPerson2.setGender( GenderType.male );

        LoginComposite login2 = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login2.setName( "mimi" );
        login2.setPassword( "mimi" );
        login2.setEnabled( true );

        contactPerson2.setLogin( login2 );
        contactPerson2.addSystemRole( contactPersonRole );

        projectOwner.addContactPerson( contactPerson );
        projectOwner.addContactPerson( contactPerson2 );
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

    public PriceRateScheduleService getPriceRateScheduleService()
    {
        return priceRateScheduleService;
    }

    public ProjectAssigneeService getProjectAssigneeService()
    {
        return projectAssigneeService;
    }

    public WorkEntryService getWorkEntryService()
    {
        return workEntryService;
    }

    public RelationshipService getRelationshipService()
    {
        return relationshipService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        return (T) compositeBuilder.newInstance();
    }
}
