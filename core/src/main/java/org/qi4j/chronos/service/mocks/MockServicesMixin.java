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
import java.lang.reflect.Method;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.PropertyValue;
import org.qi4j.api.annotation.scope.Qi4j;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.TaskAssignee;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
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
import org.qi4j.chronos.service.ParentBasedService;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.service.PriceRateService;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.service.TaskAssigneeService;
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
import org.qi4j.chronos.service.composites.PriceRateScheduleServiceComposite;
import org.qi4j.chronos.service.composites.PriceRateServiceComposite;
import org.qi4j.chronos.service.composites.ProjectAssigneeServiceComposite;
import org.qi4j.chronos.service.composites.ProjectRoleServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RelationshipServiceComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;
import org.qi4j.chronos.service.composites.SystemRoleServiceComposite;
import org.qi4j.chronos.service.composites.TaskAssigneeServiceComposite;
import org.qi4j.chronos.service.composites.TaskServiceComposite;
import org.qi4j.chronos.service.composites.UserServiceComposite;
import org.qi4j.chronos.service.composites.WorkEntryServiceComposite;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public class MockServicesMixin implements Services
{
    @Qi4j private CompositeBuilderFactory factory;

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
    private TaskAssigneeService taskAssigneeService;
    private CommentService commentService;
    private LegalConditionService legalConditionService;

    public void initServices()
    {
        accountService = newService( AccountServiceComposite.class );

        projectService = newParentBasedService( ProjectServiceComposite.class, ACCOUNT_SERVICE, accountService );

        projectRoleService = newParentBasedService( ProjectRoleServiceComposite.class, ACCOUNT_SERVICE, accountService );
        adminService = newService( AdminServiceComposite.class );
        staffService = newParentBasedService( StaffServiceComposite.class, ACCOUNT_SERVICE, accountService );

        systemRoleService = newService( SystemRoleServiceComposite.class );
        customerService = newParentBasedService( CustomerServiceComposite.class, ACCOUNT_SERVICE, accountService );
        contactPersonService = newParentBasedService( ContactPersonServiceComposite.class, "customerService", customerService );

        userService = initUserService( staffService, adminService, contactPersonService, customerService );

        priceRateScheduleService = newService( PriceRateScheduleServiceComposite.class );

        projectAssigneeService = newParentBasedService( ProjectAssigneeServiceComposite.class, "projectService", projectService );

        relationshipService = newService( RelationshipServiceComposite.class );

        contactService = newService( ContactServiceComposite.class );

        priceRateService = newService( PriceRateServiceComposite.class );

        taskService = newParentBasedService( TaskServiceComposite.class, "projectService", projectService );

        taskAssigneeService = newParentBasedService( TaskAssigneeServiceComposite.class, "taskService", taskService );

        workEntryService = newParentBasedService( WorkEntryServiceComposite.class, "taskAssigneeService", taskAssigneeService );

        commentService = newService( CommentServiceComposite.class );

        legalConditionService = newService( LegalConditionServiceComposite.class );

        initDummyData();
    }

    private <T extends Composite> T newParentBasedService( Class<T> clazz, String propertyName, Object propertyValue )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.properties( ParentBasedService.class, PropertyValue.property( propertyName, propertyValue ) );

        return (T) compositeBuilder.newInstance();
    }

    private UserService initUserService( StaffService staffService, AdminService adminService, ContactPersonService contactPersonService, CustomerService customerService )
    {
        CompositeBuilder<UserServiceComposite> compositeBuilder = factory.newCompositeBuilder( UserServiceComposite.class );

        compositeBuilder.properties( UserService.class,
                                     PropertyValue.property( "staffService", staffService ),
                                     PropertyValue.property( "adminService", adminService ),
                                     PropertyValue.property( "contactPersonService", contactPersonService ),
                                     PropertyValue.property( "customerService", customerService ) );

        return compositeBuilder.newInstance();
    }

    private void initDummyData()
    {
        AccountEntityComposite account = initAccountDummyData();

        ProjectRoleEntityComposite[] projectRoles = initProjectRoleDummyData( account );

        account.addPriceRateSchedule( newPriceRateSchedule( projectRoles ) );
        account.addPriceRateSchedule( newPriceRateSchedule( projectRoles ) );

        initSystemRoleDummyData();
        initStaffDummyData( account );
        initAdminDummyData();

        CustomerEntityComposite[] customers = initCustomerDummyData( account );

        initPriceRateScheduleDummyValue( customers, projectRoles );

        RelationshipComposite relationship = initRelationshipDummyData();

        initContactPerson( customers, relationship );

        ProjectEntityComposite project = initProjectDummyData( customers[ 0 ], account );

        ProjectAssigneeEntityComposite projectAssignee = initProjectAssigneeDummyData( project, account );

        TaskEntityComposite task = initTaskDummyData( project );

        TaskAssigneeEntityComposite taskAssignee = initTaskAssignee( task, projectAssignee );

        initWorkEntryDummyData( taskAssignee );
    }

    private TaskEntityComposite initTaskDummyData( ProjectEntityComposite project )
    {
        TaskEntityComposite task = taskService.newInstance( TaskEntityComposite.class );
        task.setCreatedDate( new Date() );
        task.setTitle( "Fix bug 10-1" );
        task.setDescription( "It cause nullpointerexception in bla bla." );

        project.addTask( task );

        return task;
    }

    private TaskAssigneeEntityComposite initTaskAssignee( TaskEntityComposite task, ProjectAssigneeEntityComposite projectAssignee )
    {
        TaskAssigneeEntityComposite taskAssignee = taskAssigneeService.newInstance( TaskAssigneeEntityComposite.class );

        taskAssignee.setProjectAssignee( projectAssignee );

        task.addTaskAssignee( taskAssignee );

        return taskAssignee;
    }

    private void initWorkEntryDummyData( TaskAssignee taskAssignee )
    {
        Calendar starCalendar = Calendar.getInstance();

        starCalendar.set( Calendar.MONTH, 8 );

        for( int i = 0; i < 20; i++ )
        {
            WorkEntryEntityComposite workEntry = workEntryService.newInstance( WorkEntryEntityComposite.class );

            workEntry.setCreatedDate( starCalendar.getTime() );
            workEntry.setStartTime( starCalendar.getTime() );

            starCalendar.add( Calendar.MINUTE, 150 );

            workEntry.setEndTime( starCalendar.getTime() );

            workEntry.setTitle( "Try to solve " + i );
            workEntry.setDescription( "try a, then b, then c, then " + i );

            taskAssignee.addWorkEntry( workEntry );
        }
    }

    private ProjectAssigneeEntityComposite initProjectAssigneeDummyData( ProjectEntityComposite project, AccountEntityComposite account )
    {
        ProjectAssigneeEntityComposite projectAssignee = projectAssigneeService.newInstance( ProjectAssigneeEntityComposite.class );

        projectAssignee.setLead( true );
        projectAssignee.setPriceRate( CloneUtil.clonePriceRate( factory, project.getPriceRateSchedule().priceRateIterator().next() ) );
        projectAssignee.setStaff( account.staffIterator().next() );

        project.addProjectAssignee( projectAssignee );
        return projectAssignee;
    }

    private ProjectEntityComposite initProjectDummyData( CustomerEntityComposite customer, AccountEntityComposite account )
    {
        ProjectEntityComposite project = projectService.newInstance( ProjectEntityComposite.class );

        project.setName( "Chronos" );
        project.setReference( "Chronos 1.2v" );

        TimeRangeComposite actualTimeRange = factory.newCompositeBuilder( TimeRangeComposite.class ).newInstance();
        TimeRangeComposite estimateTimeRange = factory.newCompositeBuilder( TimeRangeComposite.class ).newInstance();

        actualTimeRange.setEndTime( new Date() );
        actualTimeRange.setStartTime( new Date() );

        estimateTimeRange.setStartTime( new Date() );
        estimateTimeRange.setEndTime( new Date() );

        project.setActualTime( actualTimeRange );
        project.setEstimateTime( estimateTimeRange );

        project.setProjectStatus( ProjectStatus.ACTIVE );
        project.setCustomer( customer );

        project.setPrimaryContactPerson( customer.contactPersonIterator().next() );

        Iterator<ContactPersonEntityComposite> contactPersonIter = customer.contactPersonIterator();

        while( contactPersonIter.hasNext() )
        {
            project.addContactPerson( contactPersonIter.next() );
        }

        project.setPriceRateSchedule( CloneUtil.clonePriceRateSchedule( factory, customer.priceRateScheduleIterator().next() ) );

        account.addProject( project );

        return project;
    }

    private RelationshipComposite initRelationshipDummyData()
    {
        RelationshipComposite relationship = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        relationship.setRelationship( "IT Manager" );

        return relationship;
    }

    private void initPriceRateScheduleDummyValue( Customer[] customers, ProjectRoleEntityComposite[] projectRoles )
    {
        for( Customer customer : customers )
        {
            customer.addPriceRateSchedule( newPriceRateSchedule( projectRoles ) );
        }
    }

    private PriceRateScheduleComposite newPriceRateSchedule( ProjectRoleEntityComposite[] projectRoles )
    {
        PriceRateScheduleComposite priceRateSchedule = factory.newCompositeBuilder( PriceRateScheduleComposite.class ).newInstance();

        priceRateSchedule.setName( "Standard Rate" + new Random().nextInt() );
        priceRateSchedule.setCurrency( CurrencyUtil.getDefaultCurrency() );

        PriceRateComposite programmerPriceRate = newPriceRate( 100L, PriceRateType.daily, projectRoles[ 0 ] );

        PriceRateComposite consultantPriceRate = newPriceRate( 200L, PriceRateType.daily, projectRoles[ 1 ] );

        priceRateSchedule.addPriceRate( programmerPriceRate );
        priceRateSchedule.addPriceRate( consultantPriceRate );

        return priceRateSchedule;
    }

    private PriceRateComposite newPriceRate( long amount, PriceRateType priceRateType, ProjectRoleEntityComposite projectRole )
    {
        PriceRateComposite priceRate = factory.newCompositeBuilder( PriceRateComposite.class ).newInstance();

        priceRate.setAmount( amount );
        priceRate.setPriceRateType( priceRateType );
        priceRate.setProjectRole( projectRole );

        return priceRate;
    }

    private AccountEntityComposite initAccountDummyData()
    {
        AccountEntityComposite account = accountService.newInstance( AccountEntityComposite.class );

        account.setName( "Jayway Malaysia" );
        account.setReference( "Jayway Malaysia Sdn Bhd" );
        account.setEnabled( true );

        account.setAddress( newAddress( "Abc Road", "Way Center", "111", "KL", "Wilayah", "Malaysia" ) );

        accountService.save( account );

        return account;
    }

    private AddressComposite newAddress( String firstLine, String secondLine, String zipCode, String aCity, String aState, String aCountry )
    {
        AddressComposite address = factory.newCompositeBuilder( AddressComposite.class ).newInstance();
        CityComposite city = factory.newCompositeBuilder( CityComposite.class ).newInstance();
        StateComposite state = factory.newCompositeBuilder( StateComposite.class ).newInstance();
        CountryComposite country = factory.newCompositeBuilder( CountryComposite.class ).newInstance();

        address.setCity( city );

        city.setState( state );
        city.setCountry( country );

        address.getCity().setName( aCity );
        address.getCity().getCountry().setName( aCountry );
        address.getCity().getState().setName( aState );

        address.setFirstLine( firstLine );
        address.setSecondLine( secondLine );
        address.setZipCode( zipCode );

        return address;
    }

    private ProjectRoleEntityComposite[] initProjectRoleDummyData( AccountEntityComposite account )
    {
        ProjectRoleEntityComposite[] projectRoles = new ProjectRoleEntityComposite[3];

        projectRoles[ 0 ] = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        projectRoles[ 0 ].setProjectRole( "Programmer" );

        projectRoles[ 1 ] = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        projectRoles[ 1 ].setProjectRole( "Consultant" );

        projectRoles[ 2 ] = projectRoleService.newInstance( ProjectRoleEntityComposite.class );
        projectRoles[ 2 ].setProjectRole( "Project Manager" );

        account.addProjectRole( projectRoles[ 0 ] );
        account.addProjectRole( projectRoles[ 1 ] );
        account.addProjectRole( projectRoles[ 2 ] );

        return projectRoles;
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

        staff.setFirstName( "the" );
        staff.setLastName( "boss" );
        staff.setGender( GenderType.male );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "boss" );
        login.setPassword( "boss" );
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

        SystemRoleEntityComposite contactPerson = systemRoleService.newInstance( SystemRoleEntityComposite.class );
        contactPerson.setName( SystemRole.CONTACT_PERSON );
        contactPerson.setSystemRoleType( SystemRoleType.CONTACT_PERSON );

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

        customers[ 0 ].setName( "Microsoft" );
        customers[ 0 ].setReference( "Microsoft Ltd" );

        customers[ 0 ].setAddress( newAddress( "Uber Road", "Street 191", "111", "New York", "New York", "US" ) );

        customers[ 1 ] = customerService.newInstance( CustomerEntityComposite.class );

        customers[ 1 ].setName( "Sun Microsytems" );
        customers[ 1 ].setReference( "Sun Microsytems Ltd" );

        customers[ 1 ].setAddress( newAddress( "Old Town", "Street 191", "111", "New York", "New York", "US" ) );

        account.addCustomer( customers[ 0 ] );
        account.addCustomer( customers[ 1 ] );

        return customers;
    }

    private void initContactPerson( Customer[] customers, RelationshipComposite relationship )
    {
        SystemRoleEntityComposite contactPersonRole = systemRoleService.getSystemRoleByName( SystemRole.CONTACT_PERSON );

        ContactPersonEntityComposite contactPerson = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson.setFirstName( "michael" );
        contactPerson.setLastName( "Lim" );
        contactPerson.setGender( GenderType.male );
        contactPerson.setRelationship( cloneRelationship( relationship ) );

        LoginComposite login = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login.setName( "michael" );
        login.setPassword( "michael" );
        login.setEnabled( true );

        contactPerson.setLogin( login );
        contactPerson.addSystemRole( contactPersonRole );

        ContactPersonEntityComposite contactPerson2 = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson2.setFirstName( "robert" );
        contactPerson2.setLastName( "char" );
        contactPerson2.setGender( GenderType.male );
        contactPerson2.setRelationship( cloneRelationship( relationship ) );

        LoginComposite login2 = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login2.setName( "robert" );
        login2.setPassword( "robert" );
        login2.setEnabled( true );

        contactPerson2.setLogin( login2 );
        contactPerson2.addSystemRole( contactPersonRole );

        ContactPersonEntityComposite contactPerson3 = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        contactPerson3.setFirstName( "Elvin" );
        contactPerson3.setLastName( "Tan" );
        contactPerson3.setGender( GenderType.male );
        contactPerson3.setRelationship( cloneRelationship( relationship ) );

        LoginComposite login3 = factory.newCompositeBuilder( LoginComposite.class ).newInstance();

        login3.setName( "elvin" );
        login3.setPassword( "elvin" );
        login3.setEnabled( true );

        contactPerson3.setLogin( login3 );
        contactPerson3.addSystemRole( contactPersonRole );

        customers[ 0 ].addContactPerson( contactPerson );
        customers[ 0 ].addContactPerson( contactPerson2 );
        customers[ 1 ].addContactPerson( contactPerson3 );
    }

    private RelationshipComposite cloneRelationship( RelationshipComposite relationshipComposite )
    {
        RelationshipComposite newRelationship = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        newRelationship.setRelationship( relationshipComposite.getRelationship() );

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

    public TaskAssigneeService getTaskAssigneeService()
    {
        return taskAssigneeService;
    }

    public CommentService getCommentService()
    {
        return commentService;
    }

    public LegalConditionService getLegalConditionService()
    {
        return legalConditionService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        return (T) compositeBuilder.newInstance();
    }
}
