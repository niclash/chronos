/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.OngoingWorkEntry;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.PriceRateTypeEnum;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.library.general.model.Contact;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;
import org.qi4j.test.AbstractQi4jTest;

public abstract class AbstractCommonTest extends AbstractQi4jTest
{

    protected UnitOfWork unitOfWork;

    protected final SystemRole[] staffSystemRoles = new SystemRole[2];

    @Before
    @Override
    public void setUp()
        throws Exception
    {
        super.setUp();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();
    }

    @After
    @Override
    public void tearDown()
        throws Exception
    {
        if( unitOfWork != null && unitOfWork.isOpen() )
        {
            unitOfWork.discard();
        }
        super.tearDown();
    }

    @BeforeClass public static void begins() throws Exception
    {
        print( null, null, null );
    }

    @AfterClass public static void ends() throws Exception
    {
        print( null, null, null );
    }

    protected static void print( String... headers )
    {
        if( headers != null )
        {
            for( String header : headers )
            {
                log( header );
            }
        }
    }

    protected static void log( String message )
    {
        if( message == null )
        {
            System.out.println( "" );
        }
        else
        {
            System.out.println( "LOG: " + message );
        }
    }

    protected static void err( String err )
    {
        System.err.println( "ERROR: " + err );
    }

    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addEntities(
            AccountEntityComposite.class,
            AddressEntityComposite.class,
            AdminEntityComposite.class,
            CityEntityComposite.class,
            CommentEntityComposite.class,
            ContactEntityComposite.class,
            ContactPersonEntityComposite.class,
            CountryEntityComposite.class,
            CustomerEntityComposite.class,
            LegalConditionEntityComposite.class,
            LoginEntityComposite.class,
            MoneyEntityComposite.class,
            OngoingWorkEntryEntityComposite.class,
            PriceRateEntityComposite.class,
            PriceRateScheduleEntityComposite.class,
            ProjectAssigneeEntityComposite.class,
            ProjectEntityComposite.class,
            ProjectRoleEntityComposite.class,
            RelationshipEntityComposite.class,
            StaffEntityComposite.class,
            StateEntityComposite.class,
            SystemRoleEntityComposite.class,
            TaskEntityComposite.class,
            TimeRangeEntityComposite.class,
            WorkEntryEntityComposite.class
        );
        module.addServices( UuidIdentityGeneratorService.class, MemoryEntityStoreService.class );
    }

    protected final List<Project> getProjects( List<Customer> customers )
    {
        List<Project> projects = new ArrayList<Project>();

        for( Customer customer : customers )
        {
            Calendar now = Calendar.getInstance();
            now.add( Calendar.MONTH, -1 );
            Date startDate = now.getTime();
            now.add( Calendar.MONTH, 2 );
            Date endDate = now.getTime();
            ContactPerson contactPerson = customer.contactPersons().iterator().next();
            PriceRateSchedule priceRateSchedule = customer.priceRateSchedules().iterator().next();
            Project project = newProject( "Project 1", "p1", ProjectStatusEnum.ACTIVE, customer,
                                          contactPerson, priceRateSchedule,
                                          newTimeRange( startDate, endDate ) );
            projects.add( project );
        }

        return projects;
    }

    protected final List<Customer> getCustomers()
    {
        List<Customer> customers = new ArrayList<Customer>();
        customers.add( newCustomer( "Customer 1", "c1", "111", "222", "city3", "state3",
                                    "country3", "63464", "DEFAULT" ) );
        customers.add( newCustomer( "Customer 2", "c2", "1111", "2222", "city4", "state4",
                                    "country5", "43632", "DEFAULT" ) );
        customers.add( newCustomer( "Customer 3", "c3", "11111", "22222", "city5", "state5",
                                    "country6", "73422", "DEFAULT" ) );

        return customers;
    }

    protected final List<ContactPerson> getContactPersons( String customerRef )
    {
        List<ContactPerson> contactPersons = new ArrayList<ContactPerson>();
        contactPersons.add( newContactPerson( "Contact", "Person 1", "contact1" + customerRef,
                                              "contact1" + customerRef, GenderType.MALE, "Project Manager" ) );
        contactPersons.add( newContactPerson( "Contact", "Person 2", "contact2" + customerRef,
                                              "contact2" + customerRef, GenderType.FEMALE, "Project Coordinator" ) );
        contactPersons.add( newContactPerson( "Contact", "Person 3", "contact3" + customerRef,
                                              "contact3" + customerRef, GenderType.MALE, "Program Director" ) );
        contactPersons.add( newContactPerson( "Contact", "Person 4", "contact4" + customerRef,
                                              "contact4" + customerRef, GenderType.FEMALE, "Account Executive" ) );

        return contactPersons;
    }

    protected final List<PriceRate> getPriceRates()
    {
        return getPriceRates( 20L, "EUR", "Programmer" );
    }

    protected final List<PriceRate> getPriceRates( Long amount, String currencyCode, String projectRoleName )
    {
        List<PriceRate> priceRates = new ArrayList<PriceRate>();
        priceRates.add( newPriceRate( amount, currencyCode, PriceRateTypeEnum.HOURLY, projectRoleName ) );
        priceRates.add( newPriceRate( amount * 5, currencyCode, PriceRateTypeEnum.DAILY, projectRoleName ) );
        priceRates.add( newPriceRate( amount * 50, currencyCode, PriceRateTypeEnum.MONTHLY, projectRoleName ) );
        priceRates.add( newPriceRate( amount * 600, currencyCode, PriceRateTypeEnum.YEARLY, projectRoleName ) );

        return priceRates;
    }

    protected final List<Contact> getContacts()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add( newContact( "Business", "2354350943" ) );
        contacts.add( newContact( "Home", "234363765" ) );
        contacts.add( newContact( "Mobile", "097253254" ) );

        return contacts;
    }

    protected final List<Staff> getStaffs()
    {
        staffSystemRoles[ 0 ] = newSystemRole( SystemRoleTypeEnum.STAFF, SystemRole.ACCOUNT_DEVELOPER );
        staffSystemRoles[ 1 ] = newSystemRole( SystemRoleTypeEnum.STAFF, SystemRole.ACCOUNT_ADMIN );

        List<Staff> staffs = new ArrayList<Staff>();
        Staff boss = newStaff( "The", "Boss", "boss", "boss",
                               GenderType.MALE, 8000L, "EUR", staffSystemRoles );
        Staff developerA = newStaff( "The", "Developer 1", "developer1", "developer2",
                                     GenderType.MALE, 1800L, "EUR", staffSystemRoles[ 0 ] );
        Staff developerB = newStaff( "The", "Developer 2", "developer2", "developer2",
                                     GenderType.FEMALE, 1800L, "EUR", staffSystemRoles[ 0 ] );

        staffs.add( boss );
        staffs.add( developerA );
        staffs.add( developerB );

        return staffs;
    }

    protected Customer newCustomer( String customerName, String reference, String priceRateScheme )
    {
        return newCustomer( customerName, reference, "firstLine", "secondLine", "city", "state", "country", "zipCode", priceRateScheme );
    }

    protected Customer newCustomer( String customerName, String reference, String firstLine, String secondLine,
                                    String cityName, String stateName, String countryName, String zipCode,
                                    String priceRateScheme )
    {
        EntityBuilder<CustomerEntityComposite> customerBuilder = unitOfWork.newEntityBuilder( CustomerEntityComposite.class );
        customerBuilder.stateOfComposite().name().set( customerName );
        customerBuilder.stateOfComposite().reference().set( reference );
        customerBuilder.stateOfComposite().address().set( newAddress( firstLine, secondLine, cityName, stateName, countryName, zipCode ) );

        Customer customer = customerBuilder.newInstance();
        customer.priceRateSchedules().add( newPriceRateSchedule( priceRateScheme ) );
        customer.contactPersons().addAll( getContactPersons( reference ) );

        return customer;
    }

    protected PriceRateSchedule newPriceRateSchedule( String reference )
    {
        EntityBuilder<PriceRateScheduleEntityComposite> priceRateScheduleBuilder = unitOfWork.newEntityBuilder( PriceRateScheduleEntityComposite.class );
        priceRateScheduleBuilder.stateOfComposite().name().set( reference );

        PriceRateSchedule priceRateSchedule = priceRateScheduleBuilder.newInstance();
        priceRateSchedule.priceRates().addAll( getPriceRates() );

        return priceRateSchedule;
    }

    protected ContactPerson newContactPerson( String firstName, String lastName, String username, String password,
                                              GenderType genderType, String relationshipName )
    {
        EntityBuilder<ContactPersonEntityComposite> contactPersonBuilder = unitOfWork.newEntityBuilder( ContactPersonEntityComposite.class );
        contactPersonBuilder.stateOfComposite().firstName().set( firstName );
        contactPersonBuilder.stateOfComposite().lastName().set( lastName );
        contactPersonBuilder.stateOfComposite().gender().set( genderType );
        contactPersonBuilder.stateOfComposite().login().set( newLogin( username, password ) );
        contactPersonBuilder.stateOfComposite().relationship().set( newRelationship( relationshipName ) );

//        contactPersonBuilder.stateOfComposite().systemRoles().add( newSystemRole( SystemRoleTypeEnum.CONTACT_PERSON, SystemRole.CONTACT_PERSON ) );
//        contactPersonBuilder.stateOfComposite().contacts().addAll( getContacts() );
//
//        return contactPersonBuilder.newInstance();
        ContactPerson contactPerson = contactPersonBuilder.newInstance();
        contactPerson.systemRoles().add( newSystemRole( SystemRoleTypeEnum.CONTACT_PERSON, SystemRole.CONTACT_PERSON ) );
        contactPerson.contacts().addAll( getContacts() );
        return contactPerson;
    }

    protected Relationship newRelationship( String relationshipName )
    {
        EntityBuilder<RelationshipEntityComposite> relationshipBuilder = unitOfWork.newEntityBuilder( RelationshipEntityComposite.class );
        relationshipBuilder.stateOfComposite().relationship().set( relationshipName );

        return relationshipBuilder.newInstance();
    }

    protected Contact newContact( String contactType, String contactValue )
    {
        EntityBuilder<ContactEntityComposite> contactBuilder = unitOfWork.newEntityBuilder( ContactEntityComposite.class );
        contactBuilder.stateOfComposite().contactType().set( contactType );
        contactBuilder.stateOfComposite().contactValue().set( contactValue );

        return contactBuilder.newInstance();
    }

    protected PriceRate newPriceRate( Long amount, String currencyCode,
                                      PriceRateTypeEnum priceRateTypeEnum, String projectRoleName )
    {
        EntityBuilder<PriceRateEntityComposite> priceRateBuilder = unitOfWork.newEntityBuilder( PriceRateEntityComposite.class );
        priceRateBuilder.stateOfComposite().amount().set( amount );
        priceRateBuilder.stateOfComposite().currency().set( Currency.getInstance( currencyCode ) );
        priceRateBuilder.stateOfComposite().projectRole().set( newProjectRole( projectRoleName ) );
        priceRateBuilder.stateOfComposite().priceRateType().set( priceRateTypeEnum );

        return priceRateBuilder.newInstance();
    }

    protected ProjectRole newProjectRole( String projectRoleName )
    {
        EntityBuilder<ProjectRoleEntityComposite> projectRoleBuilder = unitOfWork.newEntityBuilder( ProjectRoleEntityComposite.class );
        projectRoleBuilder.stateOfComposite().name().set( projectRoleName );

        return projectRoleBuilder.newInstance();
    }

    protected SystemRole newSystemRole( SystemRoleTypeEnum systemRoleTypeEnum, String systemRoleType )
    {
        EntityBuilder<SystemRoleEntityComposite> systemRoleBuilder = unitOfWork.newEntityBuilder( SystemRoleEntityComposite.class );
        systemRoleBuilder.stateOfComposite().systemRoleType().set( systemRoleTypeEnum );
        systemRoleBuilder.stateOfComposite().name().set( systemRoleType );

        return systemRoleBuilder.newInstance();
    }

    protected Address newAddress( String firstLine, String secondLine, String cityName,
                                  String stateName, String countryName, String zipCode )
    {
        EntityBuilder<AddressEntityComposite> addressBuilder = unitOfWork.newEntityBuilder( AddressEntityComposite.class );
        addressBuilder.stateOfComposite().firstLine().set( firstLine );
        addressBuilder.stateOfComposite().secondLine().set( secondLine );
        addressBuilder.stateOfComposite().zipCode().set( zipCode );
        addressBuilder.stateOfComposite().city().set( newCity( cityName, newState( stateName ), newCountry( countryName ) ) );

        return addressBuilder.newInstance();
    }

    protected City newCity( String cityName, State state, Country country )
    {
        EntityBuilder<CityEntityComposite> cityBuilder = unitOfWork.newEntityBuilder( CityEntityComposite.class );
        cityBuilder.stateOfComposite().name().set( cityName );
        cityBuilder.stateOfComposite().state().set( state );
        cityBuilder.stateOfComposite().country().set( country );

        return cityBuilder.newInstance();
    }

    protected State newState( String stateName )
    {
        EntityBuilder<StateEntityComposite> stateBuilder = unitOfWork.newEntityBuilder( StateEntityComposite.class );
        stateBuilder.stateOfComposite().name().set( stateName );

        return stateBuilder.newInstance();
    }

    protected Country newCountry( String countryName )
    {
        EntityBuilder<CountryEntityComposite> countryBuilder = unitOfWork.newEntityBuilder( CountryEntityComposite.class );
        countryBuilder.stateOfComposite().name().set( countryName );

        return countryBuilder.newInstance();
    }

    protected Account newAccount( String name, String reference )
    {
        return newAccount( name, reference, "firstLine", "secondLine", "city", "state", "country", "zipCode" );
    }

    protected Account newAccount( String name, String reference, String firstLine, String secondLine, String cityName,
                                  String stateName, String countryName, String zipCode )
    {
        EntityBuilder<AccountEntityComposite> accountBuilder = unitOfWork.newEntityBuilder( AccountEntityComposite.class );
        accountBuilder.stateOfComposite().isEnabled().set( true );
        accountBuilder.stateOfComposite().name().set( name );
        accountBuilder.stateOfComposite().reference().set( reference );
        accountBuilder.stateOfComposite().address().set( newAddress( firstLine, secondLine, cityName, stateName,
                                                                     countryName, zipCode ) );

        return accountBuilder.newInstance();
    }

    protected Staff newStaff( String firstName, String lastName, String username, String password,
                              GenderType genderType, Long salaryAmount, String currencyCode,
                              SystemRole... systemRoles )
    {
        EntityBuilder<StaffEntityComposite> staffBuilder = unitOfWork.newEntityBuilder( StaffEntityComposite.class );
        staffBuilder.stateOfComposite().firstName().set( firstName );
        staffBuilder.stateOfComposite().lastName().set( lastName );
        staffBuilder.stateOfComposite().gender().set( genderType );
        staffBuilder.stateOfComposite().login().set( newLogin( username, password ) );
        staffBuilder.stateOfComposite().salary().set( newMoney( salaryAmount, currencyCode ) );

//        for( SystemRole systemRole : systemRoles )
//        {
//            staffBuilder.stateOfComposite().systemRoles().addAll( Arrays.asList( systemRoles ) );
//            staffBuilder.stateOfComposite().systemRoles().add( systemRole );
//        }
//
//        return staffBuilder.newInstance();
        Staff staff = staffBuilder.newInstance();
        staff.systemRoles().addAll( Arrays.asList( systemRoles ) );
        return staff;
    }

    protected void initUserData( User user, String firstName, String lastName, GenderType genderType, Login login )
    {
        user.firstName().set( firstName );
        user.lastName().set( lastName );
        user.gender().set( genderType );
        user.login().set( login );
    }

    protected Money newMoney( Long amount, String currencyCode )
    {
        EntityBuilder<MoneyEntityComposite> money = unitOfWork.newEntityBuilder( MoneyEntityComposite.class );
        money.stateOfComposite().amount().set( amount );
        money.stateOfComposite().currency().set( Currency.getInstance( currencyCode ) );

        return money.newInstance();
    }

    protected Login newLogin( String username, String password )
    {
        EntityBuilder<LoginEntityComposite> loginBuilder = unitOfWork.newEntityBuilder( LoginEntityComposite.class );
        loginBuilder.stateOfComposite().name().set( username );
        loginBuilder.stateOfComposite().password().set( password );
        loginBuilder.stateOfComposite().isEnabled().set( true );

        return loginBuilder.newInstance();
    }

    protected TimeRange newTimeRange( Date startDate, Date endDate )
    {
        EntityBuilder<TimeRangeEntityComposite> timeRangeBuilder = unitOfWork.newEntityBuilder( TimeRangeEntityComposite.class );
        timeRangeBuilder.stateOfComposite().startTime().set( startDate );
        timeRangeBuilder.stateOfComposite().endTime().set( endDate );

        return timeRangeBuilder.newInstance();
    }

    protected LegalCondition newLegalCondition( String value, String description )
    {
        EntityBuilder<LegalConditionEntityComposite> legalConditionBuilder = unitOfWork.newEntityBuilder( LegalConditionEntityComposite.class );
        legalConditionBuilder.stateOfComposite().name().set( value );
        legalConditionBuilder.stateOfComposite().description().set( description );

        return legalConditionBuilder.newInstance();
    }

    protected ProjectAssignee newProjectAssignee( boolean isLead, Staff staff, PriceRate priceRate )
    {
        EntityBuilder<ProjectAssigneeEntityComposite> projectAssigneeBuilder = unitOfWork.newEntityBuilder( ProjectAssigneeEntityComposite.class );
        projectAssigneeBuilder.stateOfComposite().isLead().set( isLead );
        projectAssigneeBuilder.stateOfComposite().staff().set( staff );
        projectAssigneeBuilder.stateOfComposite().priceRate().set( priceRate );

        return projectAssigneeBuilder.newInstance();
    }

    protected OngoingWorkEntry newOngoingWorkEntry( Date createdDate, ProjectAssignee projectAssignee )
    {
        EntityBuilder<OngoingWorkEntryEntityComposite> ongoingWorkEntryBuilder = unitOfWork.newEntityBuilder( OngoingWorkEntryEntityComposite.class );
        ongoingWorkEntryBuilder.stateOfComposite().createdDate().set( createdDate );
        ongoingWorkEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );

        return ongoingWorkEntryBuilder.newInstance();
    }

    protected Comment newComment( String comment, Date createdDate, User user )
    {
        EntityBuilder<CommentEntityComposite> commentBuilder = unitOfWork.newEntityBuilder( CommentEntityComposite.class );
        commentBuilder.stateOfComposite().text().set( comment );
        commentBuilder.stateOfComposite().createdDate().set( createdDate );
        commentBuilder.stateOfComposite().user().set( user );

        return commentBuilder.newInstance();
    }

    protected WorkEntry newWorkEntry( String title, String description, Date createdDate, TimeRange timeRange, ProjectAssignee projectAssignee )
    {
        EntityBuilder<WorkEntryEntityComposite> workEntryBuilder = unitOfWork.newEntityBuilder( WorkEntryEntityComposite.class );
        workEntryBuilder.stateOfComposite().title().set( title );
        workEntryBuilder.stateOfComposite().description().set( description );
        workEntryBuilder.stateOfComposite().createdDate().set( createdDate );
        workEntryBuilder.stateOfComposite().startTime().set( timeRange.startTime().get() );
        workEntryBuilder.stateOfComposite().endTime().set( timeRange.endTime().get() );
        workEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );

        return workEntryBuilder.newInstance();
    }

    protected Task newTask( String title, String description, Date createdDate, TaskStatusEnum taskStatus, User user )
    {
        EntityBuilder<TaskEntityComposite> taskBuilder = unitOfWork.newEntityBuilder( TaskEntityComposite.class );
        taskBuilder.stateOfComposite().title().set( title );
        taskBuilder.stateOfComposite().description().set( description );
        taskBuilder.stateOfComposite().createdDate().set( createdDate );
        taskBuilder.stateOfComposite().taskStatus().set( taskStatus );
        taskBuilder.stateOfComposite().user().set( user );

        return taskBuilder.newInstance();
    }

    protected Project newProject( String projectName, String formalReference, ProjectStatusEnum projectStatus,
                                  Customer customer, ContactPerson primaryPerson, PriceRateSchedule priceRateSchedule,
                                  TimeRange estimate )
    {
        EntityBuilder<ProjectEntityComposite> projectBuilder = unitOfWork.newEntityBuilder( ProjectEntityComposite.class );
        projectBuilder.stateOfComposite().name().set( projectName );
        projectBuilder.stateOfComposite().reference().set( formalReference );
        projectBuilder.stateOfComposite().projectStatus().set( projectStatus );
        projectBuilder.stateOfComposite().customer().set( customer );
        projectBuilder.stateOfComposite().primaryContactPerson().set( primaryPerson );
        projectBuilder.stateOfComposite().priceRateSchedule().set( priceRateSchedule );
        projectBuilder.stateOfComposite().estimateTime().set( estimate );

        return projectBuilder.newInstance();
    }

    protected UnitOfWork complete( UnitOfWork unitOfWork ) throws UnitOfWorkCompletionException
    {
        unitOfWork.complete();

        return unitOfWorkFactory.newUnitOfWork();
    }
}
