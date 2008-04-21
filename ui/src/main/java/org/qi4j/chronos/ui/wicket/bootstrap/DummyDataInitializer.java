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
package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.systemrole.SystemRoleService;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipEntityComposite;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateTypeEnum;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.Name;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.OngoingWorkEntry;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.Contact;
import java.util.Currency;
import java.util.Calendar;
import java.util.Date;

final class DummyDataInitializer
{
    private @Structure UnitOfWorkFactory unitOfWorkFactory;

    private @Service SystemRoleService roleService;

    private @Service UserService userService;

    private @Service AccountService accountService;

    private UnitOfWork unitOfWork;

    public DummyDataInitializer()
    {
    }

    public void initializeDummyData()
    {
        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        initAccounts();
        initSystemRoles();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );

        initProjectRoles();
        initAdmin();
        initStaff();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );

        initPriceRateSchedule();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );

        initCustomersAndContactPersons();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );

        initProjectsTasksAndAssignees();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );

        initWorkEntries();

        try
        {
             unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
           System.err.println( uowce.getLocalizedMessage() );
           uowce.printStackTrace();
        }
    }

    private void initWorkEntries()
    {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.DATE, -13 );
        Date createdDate = now.getTime();
        now.add( Calendar.DATE, -4 );
        Date startTime = now.getTime();
        now.add( Calendar.DATE, 3 );
        Date endTime = now.getTime();
        for( Account account : accountService.findAll() )
        {
            for( Project project : account.projects() )
            {
                for( Task task : project.tasks() )
                {
                    ProjectAssignee assignee = project.projectAssignees().iterator().next();
                    Comment comment = newComment( unitOfWork, "This is a comment.", createdDate, task.user().get() );
                    WorkEntry workEntry = newWorkEntry( unitOfWork, "Work Entry 1", "Description",
                                                        createdDate, startTime, endTime, assignee );
                    workEntry.comments().add( comment );
                    OngoingWorkEntry ongoingWorkEntry = newOngoingWorkEntry( unitOfWork, createdDate, assignee );

                    task.comments().add( comment );
                    task.workEntries().add( workEntry );
                    task.onGoingWorkEntries().add( ongoingWorkEntry );
                }
            }
        }
    }

    private void initProjectsTasksAndAssignees()
    {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MONTH, -1 );
        Date startDate = now.getTime();
        now.add( Calendar.MONTH, 2 );
        Date endDate = now.getTime();

        for( Account account : accountService.findAll() )
        {
            for( Customer customer : account.customers() )
            {
                ContactPerson contactPerson = customer.contactPersons().iterator().next();
                PriceRateSchedule priceRateSchedule = account.priceRateSchedules().iterator().next();
                Staff staff = account.staffs().iterator().next();
                PriceRate priceRate = priceRateSchedule.priceRates().iterator().next();
                ProjectAssignee projectAssignee = newProjectAssignee( unitOfWork, true, staff, priceRate );
                Task task = newTask( unitOfWork, "Task 1", "Task 1 description", startDate, TaskStatusEnum.OPEN );
                task.user().set( staff );
                LegalCondition condition = newLegalCondition( unitOfWork, "3 years", "Maintenance contract" );

                Project project = newProject( unitOfWork, "Project 1", "p1", ProjectStatusEnum.ACTIVE );
                project.customer().set( customer );
                project.primaryContactPerson().set( contactPerson );
                project.contactPersons().addAll( customer.contactPersons() );
                project.priceRateSchedule().set( priceRateSchedule );
                project.estimateTime().set( newTimeRange( unitOfWork, startDate, endDate ) );
                project.projectAssignees().add( projectAssignee );
                project.tasks().add( task );
                project.legalConditions().add( condition );

                account.projects().add( project );
            }
        }
    }

    private void initCustomersAndContactPersons()
    {
        for( Account account : accountService.findAll() )
        {
            Customer customer = newCustomer( unitOfWork, "Client A", "clientA",
                                             "line 1", "line 2", "city", "state", "country", "41412" );
            customer.priceRateSchedules().addAll( account.priceRateSchedules() );

            ContactPerson projectManager = newContactPerson( unitOfWork, "Michael", "Lim", "michael", "michael", GenderType.MALE, "Project Manager" );
            Contact mobile = newContact( unitOfWork, "Mobile", "7073247032" );
            projectManager.contacts().add( mobile );
            projectManager.systemRoles().add( roleService.getSystemRoleByName( SystemRole.CONTACT_PERSON ) );

            customer.contactPersons().add( projectManager );

            account.customers().add( customer );
        }
    }

    private void initPriceRateSchedule()
    {
        for( Account account : accountService.findAll() )
        {
            PriceRateSchedule priceRateSchedule = newPriceRateSchedule( unitOfWork, "Default" );
            priceRateSchedule.currency().set( Currency.getInstance( "EUR" ) );

            for( ProjectRole projectRole : account.projectRoles() )
            {
                PriceRate priceRate = newPriceRate( unitOfWork, 3000L, "EUR", PriceRateTypeEnum.MONTHLY );
                priceRate.projectRole().set( projectRole );
                priceRateSchedule.priceRates().add( priceRate );
            }
            account.priceRateSchedules().add( priceRateSchedule );
        }
    }

    private void initProjectRoles()
    {
        for( Account account : accountService.findAll() )
        {
            ProjectRole programmerRole = newProjectRole( unitOfWork, "Programmer" );
            ProjectRole consultantRole = newProjectRole( unitOfWork, "Consultant" );
            ProjectRole projectManagerRole = newProjectRole( unitOfWork, "Project Manager" );

            account.projectRoles().add( programmerRole );
            account.projectRoles().add( consultantRole );
            account.projectRoles().add( projectManagerRole );
        }
    }

    private void initAccounts()
    {
        Account jayway = newAccount( unitOfWork, "Jayway Malaysia", "Jayway Malaysia Sdn. Bhd." );
        Account testCorp = newAccount( unitOfWork, "Test Corp", "Test Corporation" );

        Address address = newAddress( unitOfWork, "AbcMixin Road", "Way Centre", "KL", "Wilayah Persekutuan", "Malaysia", "12345" );

        jayway = unitOfWork.dereference( jayway );
        testCorp = unitOfWork.dereference( testCorp );
        address = unitOfWork.dereference( address );

        jayway.address().set( address );
        testCorp.address().set( address );

        accountService.add( jayway );
        accountService.add( testCorp );
    }

    private void initSystemRoles()
    {
        CompositeBuilder<SystemRoleEntityComposite> systemRoleBuilder = unitOfWork.newEntityBuilder( SystemRoleEntityComposite.class );
        SystemRole adminRole = systemRoleBuilder.newInstance();
        adminRole.name().set( SystemRole.SYSTEM_ADMIN );
        adminRole.systemRoleType().set( SystemRoleTypeEnum.ADMIN );
        roleService.save( adminRole );

        SystemRole accountAdmin = systemRoleBuilder.newInstance();
        accountAdmin.name().set( SystemRole.ACCOUNT_ADMIN );
        accountAdmin.systemRoleType().set( SystemRoleTypeEnum.STAFF );
        roleService.save( accountAdmin );

        SystemRole developer = systemRoleBuilder.newInstance();
        developer.name().set( SystemRole.ACCOUNT_DEVELOPER );
        developer.systemRoleType().set( SystemRoleTypeEnum.STAFF );
        roleService.save( developer );

        SystemRole contactPerson = systemRoleBuilder.newInstance();
        contactPerson.name().set( SystemRole.CONTACT_PERSON );
        contactPerson.systemRoleType().set( SystemRoleTypeEnum.CONTACT_PERSON );
        roleService.save( contactPerson );
    }

    private void initAdmin()
    {
        Login adminLogin = newLogin( unitOfWork, "admin", "admin" );
        Admin adminUser = newUser( unitOfWork, AdminEntityComposite.class, "System", "Administrator", GenderType.MALE );
        adminUser.login().set( adminLogin );

        for( SystemRole role : roleService.findAll() )
        {
            adminUser.systemRoles().add( role );
        }
        userService.addAdmin( adminUser );
    }

    private void initStaff()
    {
        for( Account account : accountService.findAll() )
        {
            Staff boss = newUser( unitOfWork, StaffEntityComposite.class, "The", "Boss", GenderType.MALE );
            boss.login().set( newLogin( unitOfWork, "boss", "boss" ) );
            boss.salary().set( newMoney( unitOfWork, 8000L, "EUR" ) );

            Staff developer = newUser( unitOfWork, StaffEntityComposite.class, "The", "Developer", GenderType.MALE );
            developer.login().set( newLogin( unitOfWork, "developer", "developer" ) );
            developer.salary().set( newMoney( unitOfWork, 2000L, "USD" ) );

            for( SystemRole role : roleService.findAllStaffSystemRole() )
            {
                boss.systemRoles().add( role );
                if( SystemRole.ACCOUNT_DEVELOPER.equals( role.name().get() ) )
                {
                    developer.systemRoles().add( role );
                }
            }

            account.staffs().add( boss );
            account.staffs().add( developer );
        }
    }

    private boolean equals( Name name, String text )
    {
        return text.equals( name.name().get() );
    }

    protected static final UnitOfWork complete( UnitOfWork unitOfWork, UnitOfWorkFactory unitOfWorkFactory )
    {
        try
        {
            unitOfWork.complete();
            unitOfWork = unitOfWorkFactory.newUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uofwce )
        {
            System.err.println( uofwce.getLocalizedMessage() );
            uofwce.printStackTrace();

            unitOfWork.reset();
        }

        return unitOfWork;
    }

    protected static Login newLogin( UnitOfWork unitOfWork, String username, String password )
    {
        CompositeBuilder<LoginEntityComposite> loginBuilder = unitOfWork.newEntityBuilder( LoginEntityComposite.class );
        loginBuilder.stateOfComposite().name().set( username );
        loginBuilder.stateOfComposite().password().set( password );
        loginBuilder.stateOfComposite().isEnabled().set( true );

        return loginBuilder.newInstance();
    }

    protected static <T extends User, K extends T> T newUser( UnitOfWork unitOfWork, Class<K> clazz, String firstName, String lastName, GenderType gender )
    {
        CompositeBuilder<K> userBuilder = unitOfWork.newEntityBuilder( clazz );
        userBuilder.stateOfComposite().firstName().set( firstName );
        userBuilder.stateOfComposite().lastName().set( lastName );
        userBuilder.stateOfComposite().gender().set( gender );

        return userBuilder.newInstance();
    }

    protected static Account newAccount( UnitOfWork unitOfWork, String name, String reference, String firstLine, String secondLine, String cityName,
                                         String stateName, String countryName, String zipCode )
    {
        CompositeBuilder<AccountEntityComposite> accountBuilder = unitOfWork.newEntityBuilder( AccountEntityComposite.class );
        accountBuilder.stateOfComposite().isEnabled().set( true );
        accountBuilder.stateOfComposite().name().set( name );
        accountBuilder.stateOfComposite().reference().set( reference );
        accountBuilder.stateOfComposite().address().set( newAddress( unitOfWork, firstLine, secondLine, cityName, stateName,
                                                                     countryName, zipCode ) );

        return accountBuilder.newInstance();
    }

    protected static Account newAccount( UnitOfWork unitOfWork, String name, String reference )
    {
        CompositeBuilder<AccountEntityComposite> accountBuilder = unitOfWork.newEntityBuilder( AccountEntityComposite.class );
        accountBuilder.stateOfComposite().isEnabled().set( true );
        accountBuilder.stateOfComposite().name().set( name );
        accountBuilder.stateOfComposite().reference().set( reference );

        return accountBuilder.newInstance();
    }

    protected static final Address newAddress( UnitOfWork unitOfWork, String firstLine, String secondLine, String cityName,
                                               String stateName, String countryName, String zipCode )
    {
        CompositeBuilder<AddressEntityComposite> addressBuilder = unitOfWork.newEntityBuilder( AddressEntityComposite.class );
        addressBuilder.stateOfComposite().firstLine().set( firstLine );
        addressBuilder.stateOfComposite().secondLine().set( secondLine );
        addressBuilder.stateOfComposite().zipCode().set( zipCode );
        addressBuilder.stateOfComposite().city().set( newCity( unitOfWork, cityName, newState( unitOfWork, stateName ),
                                                               newCountry( unitOfWork, countryName ) ) );

        return addressBuilder.newInstance();
    }

    protected static final City newCity( UnitOfWork unitOfWork, String cityName, State state, Country country )
    {
        CompositeBuilder<CityEntityComposite> cityBuilder = unitOfWork.newEntityBuilder( CityEntityComposite.class );
        cityBuilder.stateOfComposite().name().set( cityName );
        cityBuilder.stateOfComposite().state().set( state );
        cityBuilder.stateOfComposite().country().set( country );

        return cityBuilder.newInstance();
    }

    protected static State newState( UnitOfWork unitOfWork, String stateName )
    {
        CompositeBuilder<StateEntityComposite> stateBuilder = unitOfWork.newEntityBuilder( StateEntityComposite.class );
        stateBuilder.stateOfComposite().name().set( stateName );

        return stateBuilder.newInstance();
    }

    protected static Country newCountry( UnitOfWork unitOfWork, String countryName )
    {
        CompositeBuilder<CountryEntityComposite> countryBuilder = unitOfWork.newEntityBuilder( CountryEntityComposite.class );
        countryBuilder.stateOfComposite().name().set( countryName );

        return countryBuilder.newInstance();
    }

    protected static Money newMoney( UnitOfWork unitOfWork, Long amount, String currencyCode )
    {
        CompositeBuilder<MoneyEntityComposite> money = unitOfWork.newEntityBuilder( MoneyEntityComposite.class );
        money.stateOfComposite().amount().set( amount );
        money.stateOfComposite().currency().set( Currency.getInstance( currencyCode ) );

        return money.newInstance();
    }

    protected static ProjectRole newProjectRole( UnitOfWork unitOfWork, String projectRoleName )
    {
        CompositeBuilder<ProjectRoleEntityComposite> projectRoleBuilder = unitOfWork.newEntityBuilder( ProjectRoleEntityComposite.class );
        projectRoleBuilder.stateOfComposite().name().set( projectRoleName );

        return projectRoleBuilder.newInstance();
    }

    protected static PriceRateSchedule newPriceRateSchedule( UnitOfWork unitOfWork, String reference )
    {
        CompositeBuilder<PriceRateScheduleEntityComposite> priceRateScheduleBuilder = unitOfWork.newEntityBuilder( PriceRateScheduleEntityComposite.class );
        priceRateScheduleBuilder.stateOfComposite().name().set( reference );

        return priceRateScheduleBuilder.newInstance();
    }

    protected static PriceRate newPriceRate( UnitOfWork unitOfWork, Long amount, String currencyCode,
                                      PriceRateTypeEnum priceRateTypeEnum )
    {
        CompositeBuilder<PriceRateEntityComposite> priceRateBuilder = unitOfWork.newEntityBuilder( PriceRateEntityComposite.class );
        priceRateBuilder.stateOfComposite().amount().set( amount );
        priceRateBuilder.stateOfComposite().currency().set( Currency.getInstance(currencyCode) );
        priceRateBuilder.stateOfComposite().priceRateType().set( priceRateTypeEnum );

        return priceRateBuilder.newInstance();
    }

    protected static Customer newCustomer( UnitOfWork unitOfWork, String customerName, String reference, String firstLine, String secondLine,
                                    String cityName, String stateName, String countryName, String zipCode )
    {
        CompositeBuilder<CustomerEntityComposite> customerBuilder = unitOfWork.newEntityBuilder( CustomerEntityComposite.class );
        customerBuilder.stateOfComposite().name().set( customerName );
        customerBuilder.stateOfComposite().reference().set( reference );
        customerBuilder.stateOfComposite().address().set(
            newAddress( unitOfWork, firstLine, secondLine, cityName, stateName, countryName, zipCode ));

        return customerBuilder.newInstance();
    }

    protected static ContactPerson newContactPerson( UnitOfWork unitOfWork, String firstName, String lastName, String username, String password,
                            GenderType genderType, String relationshipName )
    {
        CompositeBuilder<ContactPersonEntityComposite> contactPersonBuilder = unitOfWork.newEntityBuilder( ContactPersonEntityComposite.class );
        contactPersonBuilder.stateOfComposite().firstName().set( firstName );
        contactPersonBuilder.stateOfComposite().lastName().set( lastName );
        contactPersonBuilder.stateOfComposite().gender().set( genderType );
        contactPersonBuilder.stateOfComposite().login().set( newLogin( unitOfWork, username, password ) );
        contactPersonBuilder.stateOfComposite().relationship().set( newRelationship( unitOfWork, relationshipName ) );

        return contactPersonBuilder.newInstance();
    }

    protected static Relationship newRelationship( UnitOfWork unitOfWork, String relationshipName )
    {
        CompositeBuilder<RelationshipEntityComposite> relationshipBuilder = unitOfWork.newEntityBuilder( RelationshipEntityComposite.class );
        relationshipBuilder.stateOfComposite().relationship().set( relationshipName );

        return relationshipBuilder.newInstance();
    }

    protected static Contact newContact( UnitOfWork unitOfWork, String contactType, String contactValue )
    {
        CompositeBuilder<ContactEntityComposite> contactBuilder = unitOfWork.newEntityBuilder( ContactEntityComposite.class );
        contactBuilder.stateOfComposite().contactType().set( contactType );
        contactBuilder.stateOfComposite().contactValue().set( contactValue );

        return contactBuilder.newInstance();
    }

    protected static Project newProject( UnitOfWork unitOfWork, String projectName,
                                         String formalReference, ProjectStatusEnum projectStatus )
    {
        CompositeBuilder<ProjectEntityComposite> projectBuilder = unitOfWork.newEntityBuilder( ProjectEntityComposite.class );
        projectBuilder.stateOfComposite().name().set( projectName );
        projectBuilder.stateOfComposite().reference().set( formalReference );
        projectBuilder.stateOfComposite().projectStatus().set( projectStatus );

        return projectBuilder.newInstance();
    }

    protected static TimeRange newTimeRange( UnitOfWork unitOfWork, Date startDate, Date endDate )
    {
        CompositeBuilder<TimeRangeEntityComposite> timeRangeBuilder = unitOfWork.newEntityBuilder( TimeRangeEntityComposite.class );
        timeRangeBuilder.stateOfComposite().startTime().set( startDate );
        timeRangeBuilder.stateOfComposite().endTime().set( endDate );

        return timeRangeBuilder.newInstance();
    }

    protected static ProjectAssignee newProjectAssignee( UnitOfWork unitOfWork, boolean isLead, Staff staff, PriceRate priceRate )
    {
        CompositeBuilder<ProjectAssigneeEntityComposite> projectAssigneeBuilder = unitOfWork.newEntityBuilder( ProjectAssigneeEntityComposite.class );
        projectAssigneeBuilder.stateOfComposite().isLead().set( isLead );
        projectAssigneeBuilder.stateOfComposite().staff().set( staff );
        projectAssigneeBuilder.stateOfComposite().priceRate().set( priceRate );

        return projectAssigneeBuilder.newInstance();
    }

    protected static LegalCondition newLegalCondition( UnitOfWork unitOfWork, String value, String description )
    {
        CompositeBuilder<LegalConditionEntityComposite> legalConditionBuilder = unitOfWork.newEntityBuilder( LegalConditionEntityComposite.class );
        legalConditionBuilder.stateOfComposite().name().set( value );
        legalConditionBuilder.stateOfComposite().description().set( description );

        return legalConditionBuilder.newInstance();
    }

    protected static Task newTask( UnitOfWork unitOfWork, String title, String description, Date createdDate, TaskStatusEnum taskStatus )
    {
        CompositeBuilder<TaskEntityComposite> taskBuilder = unitOfWork.newEntityBuilder( TaskEntityComposite.class );
        taskBuilder.stateOfComposite().title().set( title );
        taskBuilder.stateOfComposite().description().set( description );
        taskBuilder.stateOfComposite().createdDate().set( createdDate );
        taskBuilder.stateOfComposite().taskStatus().set( taskStatus );

        return taskBuilder.newInstance();
    }

    protected static Comment newComment( UnitOfWork unitOfWork, String comment, Date createdDate, User user )
    {
        CompositeBuilder<CommentEntityComposite> commentBuilder = unitOfWork.newEntityBuilder( CommentEntityComposite.class );
        commentBuilder.stateOfComposite().text().set( comment );
        commentBuilder.stateOfComposite().createdDate().set( createdDate );
        commentBuilder.stateOfComposite().user().set( user );

        return commentBuilder.newInstance();
    }

    protected static WorkEntry newWorkEntry( UnitOfWork unitOfWork, String title, String description, Date createdDate,
                                             Date startTime, Date endTime, ProjectAssignee projectAssignee )
    {
        CompositeBuilder<WorkEntryEntityComposite> workEntryBuilder = unitOfWork.newEntityBuilder( WorkEntryEntityComposite.class );
        workEntryBuilder.stateOfComposite().title().set( title );
        workEntryBuilder.stateOfComposite().description().set( description );
        workEntryBuilder.stateOfComposite().createdDate().set( createdDate );
        workEntryBuilder.stateOfComposite().startTime().set( startTime );
        workEntryBuilder.stateOfComposite().endTime().set( endTime );
        workEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );

        return workEntryBuilder.newInstance();
    }

    protected static OngoingWorkEntry newOngoingWorkEntry( UnitOfWork unitOfWork, Date createdDate, ProjectAssignee projectAssignee )
    {
        CompositeBuilder<OngoingWorkEntryEntityComposite> ongoingWorkEntryBuilder = unitOfWork.newEntityBuilder( OngoingWorkEntryEntityComposite.class );
        ongoingWorkEntryBuilder.stateOfComposite().createdDate().set( createdDate );
        ongoingWorkEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );

        return ongoingWorkEntryBuilder.newInstance();
    }
}
