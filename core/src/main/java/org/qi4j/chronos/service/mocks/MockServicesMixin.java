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

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.qi4j.association.Association;
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.AdminService;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.service.PriceRateService;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.service.WorkEntryService;
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
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.TaskServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.chronos.service.composites.WorkEntryServiceComposite;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;
import org.qi4j.library.general.model.City;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public class MockServicesMixin implements Services
{
    @Structure CompositeBuilderFactory factory;

    private final static String ACCOUNT_SERVICE = "accountService";

    private AccountService accountService;
    private ProjectService projectService;
    private ProjectRoleService projectRoleService;
    private StaffService staffService;
    private UserService userService;
    private SystemRoleService systemRoleService;
    private CustomerService customerService;
    private AdminService adminService;
    private ContactPersonService contactPersonService;
    private PriceRateScheduleService priceRateScheduleService;
    private ProjectAssigneeService projectAssigneeService;
    private WorkEntryService workEntryService;
    private RelationshipService relationshipService;
    private ContactService contactService;
    private PriceRateService priceRateService;
    private TaskService taskService;
    private CommentService commentService;
    private LegalConditionService legalConditionService;
    private OngoingWorkEntryService ongoingWorkEntryService;

    public void initServices()
    {
        accountService = newService( AccountServiceComposite.class );

        projectService = newParentBasedService( ProjectServiceComposite.class, accountService );

        projectRoleService = newService( ProjectRoleServiceComposite.class );
        adminService = newService( AdminServiceComposite.class );
        staffService = newParentBasedService( StaffServiceComposite.class, accountService );

        systemRoleService = newService( SystemRoleServiceComposite.class );
        customerService = newParentBasedService( CustomerServiceComposite.class, accountService );
        contactPersonService = newParentBasedService( ContactPersonServiceComposite.class, customerService );

        userService = initUserService( staffService, adminService, contactPersonService, customerService );

        priceRateScheduleService = newService( PriceRateScheduleServiceComposite.class );

        projectAssigneeService = initProjectAssigneeService( "projectService", projectService );

        relationshipService = newService( RelationshipServiceComposite.class );

        contactService = newService( ContactServiceComposite.class );

        priceRateService = newService( PriceRateServiceComposite.class );

        taskService = newParentBasedService( TaskServiceComposite.class, projectService );

        workEntryService = newService( WorkEntryServiceComposite.class );

        commentService = newService( CommentServiceComposite.class );

        legalConditionService = newService( LegalConditionServiceComposite.class );

        ongoingWorkEntryService = newParentBasedService( OngoingWorkEntryServiceComposite.class, taskService );

        initDummyData();
    }

    private <T extends Composite> T newParentBasedService( Class<T> clazz, Object propertyValue )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.use( propertyValue );

        return (T) compositeBuilder.newInstance();
    }

    private UserService initUserService( StaffService staffService, AdminService adminService, ContactPersonService contactPersonService, CustomerService customerService )
    {
        CompositeBuilder<UserServiceComposite> compositeBuilder = factory.newCompositeBuilder( UserServiceComposite.class );

        compositeBuilder.use( staffService, adminService, contactPersonService, customerService );

        return compositeBuilder.newInstance();
    }

    private void initDummyData()
    {
        AccountEntityComposite account = initAccountDummyData();

        ProjectRoleComposite[] projectRoles = initProjectRoleDummyData( account );

        SetAssociation<PriceRateScheduleComposite> priceRateSchedules = account.priceRateSchedules();
        priceRateSchedules.add( newPriceRateSchedule( projectRoles ) );
        priceRateSchedules.add( newPriceRateSchedule( projectRoles ) );

        initSystemRoleDummyData();
        initStaffDummyData( account );
        initAdminDummyData();

        CustomerEntityComposite[] customers = initCustomerDummyData( account );

        initPriceRateScheduleDummyValue( customers, projectRoles );

        RelationshipComposite relationship = initRelationshipDummyData();

        initContactPerson( customers, relationship );

        ProjectEntityComposite project = initProjectDummyData( customers[ 0 ], account );

        ProjectAssigneeEntityComposite[] projectAssignees = initProjectAssigneeDummyData( project, account );

        TaskEntityComposite[] tasks = initTaskDummyData( project, account.staffs().iterator().next() );

        initOngoingWorkEntry( tasks, projectAssignees );

        initWorkEntryDummyData( tasks[ 0 ], projectAssignees[ 0 ] );
    }

    private ProjectAssigneeService initProjectAssigneeService( String propertyName, Object propertyValue )
    {
        CompositeBuilder<ProjectAssigneeServiceComposite> compositeBuilder = factory.newCompositeBuilder( ProjectAssigneeServiceComposite.class );

        // TODO Fix this. What is it trying to do ?? compositeBuilder.properties( ParentBasedService.class, PropertyValue.property( propertyName, propertyValue ) );

        return compositeBuilder.newInstance();
    }

    private void initOngoingWorkEntry( TaskEntityComposite[] tasks, ProjectAssigneeEntityComposite[] projectAssignees )
    {
        for( int i = 0; i < tasks.length; i++ )
        {
            OngoingWorkEntryEntityComposite workEntry = ongoingWorkEntryService.newInstance( OngoingWorkEntryEntityComposite.class );

            workEntry.createdDate().set( new Date() );

            Association<ProjectAssigneeEntityComposite> projectAssignee = workEntry.projectAssignee();
            if( i % 2 == 0 )
            {
                projectAssignee.set( projectAssignees[ 0 ] );
            }
            else
            {
                projectAssignee.set( projectAssignees[ 1 ] );
            }

            tasks[ i ].onGoingWorkEntries().add( workEntry );
        }
    }

    private TaskEntityComposite[] initTaskDummyData( ProjectEntityComposite project, StaffEntityComposite staff )
    {
        TaskEntityComposite[] tasks = new TaskEntityComposite[20];

        for( int i = 0; i < tasks.length; i++ )
        {
            tasks[ i ] = taskService.newInstance( TaskEntityComposite.class );
            tasks[ i ].createdDate().set( new Date() );
            tasks[ i ].title().set( "Fix bug 10-" + i );
            tasks[ i ].description().set( "It cause nullpointerexception in bla bla." );
            tasks[ i ].user().set( staff );
            tasks[ i ].taskStatus().set( TaskStatus.OPEN );

            project.tasks().add( tasks[ i ] );
        }

        return tasks;
    }

    private void initWorkEntryDummyData( Task task, ProjectAssigneeEntityComposite projectAssignee )
    {
        Calendar starCalendar = Calendar.getInstance();

        starCalendar.set( Calendar.MONTH, 8 );

        for( int i = 0; i < 20; i++ )
        {
            WorkEntryEntityComposite workEntry = workEntryService.newInstance( WorkEntryEntityComposite.class );

            workEntry.createdDate().set( starCalendar.getTime() );
            workEntry.startTime().set( starCalendar.getTime() );

            starCalendar.add( Calendar.MINUTE, 150 );

            workEntry.endTime().set( starCalendar.getTime() );

            workEntry.title().set( "Try to solve " + i );
            workEntry.description().set( "try a, then b, then c, then " + i );
            workEntry.projectAssignee().set( projectAssignee );

            task.workEntries().add( workEntry );
        }
    }

    private ProjectAssigneeEntityComposite[] initProjectAssigneeDummyData( ProjectEntityComposite project, AccountEntityComposite account )
    {
        ProjectAssigneeEntityComposite[] dummyProjectAssignees = new ProjectAssigneeEntityComposite[2];

        PriceRateScheduleComposite projectPriceRateSchedule = project.priceRateSchedule().get();
        PriceRateComposite priceRate = projectPriceRateSchedule.priceRates().iterator().next();

        Iterator<StaffEntityComposite> staffIter = account.staffs().iterator();

        SetAssociation<ProjectAssigneeEntityComposite> projectAssignees = project.projectAssignees();
        projectAssignees.add( dummyProjectAssignees[ 0 ] = createProjectAssignee( true, priceRate, staffIter.next() ) );
        projectAssignees.add( dummyProjectAssignees[ 1 ] = createProjectAssignee( true, priceRate, staffIter.next() ) );

        return dummyProjectAssignees;
    }

    private ProjectAssigneeEntityComposite createProjectAssignee( boolean isLead, PriceRateComposite priceRate, StaffEntityComposite staff )
    {
        ProjectAssigneeEntityComposite projectAssignee = projectAssigneeService.newInstance( ProjectAssigneeEntityComposite.class );

        projectAssignee.isLead().set( isLead );
        projectAssignee.priceRate().set( CloneUtil.clonePriceRate( factory, priceRate ) );
        projectAssignee.staff().set( staff );

        return projectAssignee;
    }

    private ProjectEntityComposite initProjectDummyData( CustomerEntityComposite customer, AccountEntityComposite account )
    {
        ProjectEntityComposite project = projectService.newInstance( ProjectEntityComposite.class );

        project.name().set( "Chronos" );
        project.reference().set( "Chronos 1.2v" );

        TimeRangeComposite actualTimeRange = factory.newCompositeBuilder( TimeRangeComposite.class ).newInstance();
        TimeRangeComposite estimateTimeRange = factory.newCompositeBuilder( TimeRangeComposite.class ).newInstance();

        actualTimeRange.endTime().set( new Date() );
        actualTimeRange.startTime().set( new Date() );

        estimateTimeRange.startTime().set( new Date() );
        estimateTimeRange.endTime().set( new Date() );

        project.actualTime().set( actualTimeRange );
        project.estimateTime().set( estimateTimeRange );

        project.projectStatus().set( ProjectStatus.ACTIVE );
        project.customer().set( customer );

        SetAssociation<ContactPersonEntityComposite> customerContacts = customer.contactPersons();
        project.primaryContactPerson().set( customerContacts.iterator().next() );

        SetAssociation<ContactPersonEntityComposite> projectContacts = project.contactPersons();
        projectContacts.addAll( customerContacts );

        Association<PriceRateScheduleComposite> projectPriceRateSchedule = project.priceRateSchedule();
        projectPriceRateSchedule.set( CloneUtil.clonePriceRateSchedule( factory, customer.priceRateSchedules().iterator().next() ) );

        account.projects().add( project );

        return project;
    }

    private RelationshipComposite initRelationshipDummyData()
    {
        RelationshipComposite relationship = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        relationship.relationship().set( "IT Manager" );

        return relationship;
    }

    private void initPriceRateScheduleDummyValue( Customer[] customers, ProjectRoleComposite[] projectRoles )
    {
        for( Customer customer : customers )
        {
            SetAssociation<PriceRateScheduleComposite> customerPriceRateSchedules = customer.priceRateSchedules();
            customerPriceRateSchedules.add( newPriceRateSchedule( projectRoles ) );
        }
    }

    private PriceRateScheduleComposite newPriceRateSchedule( ProjectRoleComposite[] projectRoles )
    {
        PriceRateScheduleComposite priceRateSchedule = factory.newCompositeBuilder( PriceRateScheduleComposite.class ).newInstance();

        priceRateSchedule.name().set( "Standard Rate" + new Random().nextInt() );
        priceRateSchedule.currency().set( CurrencyUtil.getDefaultCurrency() );

        PriceRateComposite programmerPriceRate = newPriceRate( 100L, PriceRateType.DAILY, projectRoles[ 0 ] );

        PriceRateComposite consultantPriceRate = newPriceRate( 200L, PriceRateType.DAILY, projectRoles[ 1 ] );

        SetAssociation<PriceRateComposite> priceRates = priceRateSchedule.priceRates();
        priceRates.add( programmerPriceRate );
        priceRates.add( consultantPriceRate );

        return priceRateSchedule;
    }

    private PriceRateComposite newPriceRate( long amount, PriceRateType priceRateType, ProjectRoleComposite projectRole )
    {
        PriceRateComposite priceRate = factory.newCompositeBuilder( PriceRateComposite.class ).newInstance();

        priceRate.amount().set( amount );
        priceRate.priceRateType().set( priceRateType );
        priceRate.projectRole().set( projectRole );

        return priceRate;
    }

    private AccountEntityComposite initAccountDummyData()
    {
        AccountEntityComposite account = accountService.newInstance( AccountEntityComposite.class );

        account.name().set( "Jayway Malaysia" );
        account.reference().set( "Jayway Malaysia Sdn Bhd" );
        account.isEnabled().set( true );

        account.address().set( newAddress( "AbcMixin Road", "Way Center", "111", "KL", "Wilayah", "Malaysia" ) );

        accountService.save( account );

        return account;
    }

    private AddressComposite newAddress( String firstLine, String secondLine, String zipCode, String aCity, String aState, String aCountry )
    {
        AddressComposite address = factory.newCompositeBuilder( AddressComposite.class ).newInstance();
        CityComposite city = factory.newCompositeBuilder( CityComposite.class ).newInstance();
        StateComposite state = factory.newCompositeBuilder( StateComposite.class ).newInstance();
        CountryComposite country = factory.newCompositeBuilder( CountryComposite.class ).newInstance();

        address.city().set( city );

        city.state().set( state );
        city.country().set( country );

        City addressCity = address.city().get();
        addressCity.name().set( aCity );
        addressCity.country().get().name().set( aCountry );
        addressCity.state().get().name().set( aState );

        address.firstLine().set( firstLine );
        address.secondLine().set( secondLine );
        address.zipCode().set( zipCode );

        return address;
    }

    private ProjectRoleComposite[] initProjectRoleDummyData( AccountEntityComposite account )
    {
        ProjectRoleComposite[] dummyProjectRoles = new ProjectRoleComposite[3];

        dummyProjectRoles[ 0 ] = factory.newCompositeBuilder( ProjectRoleComposite.class ).newInstance();
        dummyProjectRoles[ 0 ].name().set( "Programmer" );

        dummyProjectRoles[ 1 ] = factory.newCompositeBuilder( ProjectRoleComposite.class ).newInstance();
        dummyProjectRoles[ 1 ].name().set( "Consultant" );

        dummyProjectRoles[ 2 ] = factory.newCompositeBuilder( ProjectRoleComposite.class ).newInstance();
        dummyProjectRoles[ 2 ].name().set( "Project Manager" );

        SetAssociation<ProjectRoleComposite> projectRoles = account.projectRoles();
        projectRoles.add( dummyProjectRoles[ 0 ] );
        projectRoles.add( dummyProjectRoles[ 1 ] );
        projectRoles.add( dummyProjectRoles[ 2 ] );

        return dummyProjectRoles;
    }

    private void initAdminDummyData()
    {
        AdminEntityComposite admin = adminService.newInstance( AdminEntityComposite.class );

        admin.firstName().set( "admin" );
        admin.lastName().set( "admin" );
        admin.gender().set( GenderType.MALE );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.name().set( "admin" );
        login.password().set( "admin" );
        login.isEnabled().set( true );

        admin.login().set( login );

        List<SystemRoleComposite> systemRoleList = systemRoleService.findAll();
        SetAssociation<SystemRoleComposite> adminSystemRoles = admin.systemRoles();
        adminSystemRoles.addAll( systemRoleList );

        adminService.save( admin );
    }

    private void initStaffDummyData( AccountEntityComposite account )
    {
        StaffEntityComposite boss = newStaff( "the", "boss", GenderType.MALE );

        boss.login().set( newLogin( "boss", "boss", true ) );

        boss.salary().set( createMoney( 0l, Currency.getInstance( "USD" ) ) );


        StaffEntityComposite developer = newStaff( "the", "developer", GenderType.MALE );

        developer.login().set( newLogin( "developer", "developer", true ) );
        developer.salary().set( createMoney( 0l, Currency.getInstance( "USD" ) ) );

        List<SystemRoleComposite> systemRoleList = systemRoleService.findAllStaffSystemRole();

        for( SystemRoleComposite systemRole : systemRoleList )
        {
            SetAssociation<SystemRoleComposite> bossSystemRoles = boss.systemRoles();
            bossSystemRoles.add( systemRole );

            if( systemRole.name().get().equals( SystemRole.ACCOUNT_DEVELOPER ) )
            {
                SetAssociation<SystemRoleComposite> developerSystemRoles = developer.systemRoles();
                developerSystemRoles.add( systemRole );
            }
        }

        SetAssociation<StaffEntityComposite> accountStaffs = account.staffs();
        accountStaffs.add( boss );
        accountStaffs.add( developer );
    }

    private StaffEntityComposite newStaff( String firstName, String lastName, GenderType gender )
    {
        StaffEntityComposite staff = staffService.newInstance( StaffEntityComposite.class );

        staff.firstName().set( firstName );
        staff.lastName().set( lastName );
        staff.gender().set( gender );

        return staff;
    }

    private MoneyComposite createMoney( long amount, Currency currency )
    {
        MoneyComposite money = factory.newCompositeBuilder( MoneyComposite.class ).newInstance();

        money.amount().set( amount );
        money.currency().set( currency );

        return money;
    }

    private LoginComposite newLogin( String loginId, String password, boolean enabled )
    {
        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.name().set( loginId );
        login.password().set( password );
        login.isEnabled().set( enabled );

        return login;
    }

    private void initSystemRoleDummyData()
    {
        SystemRoleComposite admin = factory.newCompositeBuilder( SystemRoleComposite.class ).newInstance();
        admin.name().set( SystemRole.SYSTEM_ADMIN );
        admin.systemRoleType().set( SystemRoleType.ADMIN );

        SystemRoleComposite accountAdmin = factory.newCompositeBuilder( SystemRoleComposite.class ).newInstance();
        accountAdmin.name().set( SystemRole.ACCOUNT_ADMIN );
        accountAdmin.systemRoleType().set( SystemRoleType.STAFF );

        SystemRoleComposite developer = factory.newCompositeBuilder( SystemRoleComposite.class ).newInstance();
        developer.name().set( SystemRole.ACCOUNT_DEVELOPER );
        developer.systemRoleType().set( SystemRoleType.STAFF );

        SystemRoleComposite contactPerson = factory.newCompositeBuilder( SystemRoleComposite.class ).newInstance();
        contactPerson.name().set( SystemRole.CONTACT_PERSON );
        contactPerson.systemRoleType().set( SystemRoleType.CONTACT_PERSON );

        systemRoleService.save( admin );
        systemRoleService.save( accountAdmin );
        systemRoleService.save( developer );
        systemRoleService.save( contactPerson );

        systemRoleService.findAll();
    }

    private CustomerEntityComposite[] initCustomerDummyData( AccountEntityComposite account )
    {
        CustomerEntityComposite[] customers = new CustomerEntityComposite[2];

        customers[ 0 ] = customerService.newInstance( CustomerEntityComposite.class );

        customers[ 0 ].name().set( "Microsoft" );
        customers[ 0 ].reference().set( "Microsoft Ltd" );

        customers[ 0 ].address().set( newAddress( "Uber Road", "Street 191", "111", "New York", "New York", "US" ) );

        customers[ 1 ] = customerService.newInstance( CustomerEntityComposite.class );

        customers[ 1 ].name().set( "Sun Microsytems" );
        customers[ 1 ].reference().set( "Sun Microsytems Ltd" );

        customers[ 1 ].address().set( newAddress( "Old Town", "Street 191", "111", "New York", "New York", "US" ) );

        SetAssociation<CustomerEntityComposite> accountCustomers = account.customers();
        accountCustomers.add( customers[ 0 ] );
        accountCustomers.add( customers[ 1 ] );

        return customers;
    }

    private void initContactPerson( Customer[] customers, RelationshipComposite relationship )
    {
        SystemRoleComposite contactPersonRole = systemRoleService.getSystemRoleByName( SystemRole.CONTACT_PERSON );

        ContactPersonEntityComposite contactPerson = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson.firstName().set( "michael" );
        contactPerson.lastName().set( "Lim" );
        contactPerson.gender().set( GenderType.MALE );
        contactPerson.relationship().set( cloneRelationship( relationship ) );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.name().set( "michael" );
        login.password().set( "michael" );
        login.isEnabled().set( true );

        contactPerson.login().set( login );
        contactPerson.systemRoles().add( contactPersonRole );

        ContactPersonEntityComposite contactPerson2 = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson2.firstName().set( "robert" );
        contactPerson2.lastName().set( "char" );
        contactPerson2.gender().set( GenderType.MALE );
        contactPerson2.relationship().set( cloneRelationship( relationship ) );

        LoginComposite login2 = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login2.name().set( "robert" );
        login2.password().set( "robert" );
        login2.isEnabled().set( true );

        contactPerson2.login().set( login2 );
        contactPerson2.systemRoles().add( contactPersonRole );

        ContactPersonEntityComposite contactPerson3 = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson3.firstName().set( "Elvin" );
        contactPerson3.lastName().set( "Tan" );
        contactPerson3.gender().set( GenderType.MALE );
        contactPerson3.relationship().set( cloneRelationship( relationship ) );

        LoginComposite login3 = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login3.name().set( "elvin" );
        login3.password().set( "elvin" );
        login3.isEnabled().set( true );

        contactPerson3.login().set( login3 );
        contactPerson3.systemRoles().add( contactPersonRole );

        SetAssociation<ContactPersonEntityComposite> firstCustomerContacts = customers[ 0 ].contactPersons();
        firstCustomerContacts.add( contactPerson );
        firstCustomerContacts.add( contactPerson2 );
        customers[ 1 ].contactPersons().add( contactPerson3 );
    }

    private RelationshipComposite cloneRelationship( RelationshipComposite relationshipComposite )
    {
        RelationshipComposite newRelationship = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        newRelationship.relationship().set( relationshipComposite.relationship().get() );

        return newRelationship;
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

    public CustomerService getCustomerService()
    {
        return customerService;
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

    public ContactService getContactService()
    {
        return contactService;
    }

    public PriceRateService getPriceRateService()
    {
        return priceRateService;
    }

    public TaskService getTaskService()
    {
        return taskService;
    }

    public CommentService getCommentService()
    {
        return commentService;
    }

    public LegalConditionService getLegalConditionService()
    {
        return legalConditionService;
    }

    public OngoingWorkEntryService getOngoingWorkEntryService()
    {
        return ongoingWorkEntryService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        return (T) compositeBuilder.newInstance();
    }
}
