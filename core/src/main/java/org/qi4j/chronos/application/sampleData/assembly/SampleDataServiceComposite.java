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
package org.qi4j.chronos.application.sampleData.assembly;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.application.sampleData.SampleDataService;
import org.qi4j.chronos.domain.model.account.AccountFactory;

@Mixins( SampleDataServiceComposite.SampleDataServiceMixin.class )
interface SampleDataServiceComposite extends SampleDataService, ServiceComposite
{
    class SampleDataServiceMixin
        implements SampleDataService
    {
        @Structure private UnitOfWorkFactory uowf;
        @Service private AccountFactory accountFactory;

        public void populate()
            throws UnitOfWorkCompletionException
        {
            UnitOfWork uow = uowf.newUnitOfWork();
            try
            {
//                initAccounts();
//
//                initAdmin();
//                initProjectRolesAndStaff();
//
//                initPriceRateSchedule();
//                initCustomersAndContactPersons();
//                initProjectsTasksAndAssignees();
//                initWorkEntries();
            }
            finally
            {
                uow.complete();
            }
        }

//        /**
//         * Creates 2 default accounts.
//         */
//        private void initAccounts()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            Address address = newAddress( unitOfWork, "AbcMixin Road", "Way Centre", "KL",
//                                          "Wilayah Persekutuan", "Malaysia", "12345" );
//            Account jayway = newAccount( "Jayway Malaysia", "Jayway Malaysia Sdn. Bhd." );
//            jayway.address().set( address );
//
//            address = newAddress( unitOfWork, "AbcMixin Road", "Way Centre", "KL",
//                                  "Wilayah Persekutuan", "Malaysia", "12345" );
//            Account testCorp = newAccount( "Test Corp", "Test Corporation" );
//            testCorp.address().set( address );
//
//            complete( unitOfWork );
//        }
//
//        private Iterable<Account> getAllAccounts( UnitOfWork unitOfWork )
//        {
//            QueryBuilderFactory queryBuilderFactory = unitOfWork.queryBuilderFactory();
//            QueryBuilder<Account> accountQueryBuilder = queryBuilderFactory.newQueryBuilder( Account.class );
//            return accountQueryBuilder.newQuery();
//        }
//
//        /**
//         * Creates work entry per projects and tasks.
//         */
//        private void initWorkEntries()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            ChronosSystem system = unitOfWork.newEntity( ChronosSystem.class );
//
//            Iterable<Account> accounts = getAllAccounts( unitOfWork );
//
//            for( Account account : accounts )
//            {
//                Calendar now = Calendar.getInstance();
//                Date createdDate = now.getTime();
//                Date endTime = now.getTime();
//                now.add( Calendar.DATE, -30 );
//                Date startTime = now.getTime();
//
//                account = unitOfWork.dereference( account );
//                for( Project project : account.projects() )
//                {
//                    ProjectAssignee assignee = project.projectAssignees().iterator().next();
//                    WorkEntry projectWorkEntry = newWorkEntry( unitOfWork, "Project work entry", "Description",
//                                                               createdDate, startTime, endTime, assignee );
//                    Comment projectComment = newComment( unitOfWork, "Project work entry comment.",
//                                                         createdDate, assignee.staff().get() );
//                    projectWorkEntry.comments().add( projectComment );
//                    project.workEntries().add( projectWorkEntry );
//
//                    for( int j = 0; j < 1; j++ )
//                    {
//                        now.add( Calendar.HOUR_OF_DAY, -4 );
//                        startTime = now.getTime();
//                        now.add( Calendar.HOUR_OF_DAY, 2 );
//                        endTime = now.getTime();
//                        now.add( Calendar.HOUR_OF_DAY, 2 );
//                        createdDate = now.getTime();
//                        now.add( Calendar.DATE, 1 );
//
//                        for( Task task : project.tasks() )
//                        {
//                            WorkEntry workEntry = newWorkEntry( unitOfWork, "Work Entry " + j, "Description",
//                                                                createdDate, startTime, endTime, assignee );
//                            Comment comment = newComment( unitOfWork, "This is a comment.",
//                                                          createdDate, task.user().get() );
//                            workEntry.comments().add( comment );
//                            task.comments().add( newComment( unitOfWork, "This is a comment.",
//                                                             createdDate, task.user().get() ) );
//                            task.workEntries().add( workEntry );
//
//                            OngoingWorkEntry ongoingWorkEntry = newOngoingWorkEntry( unitOfWork, createdDate, assignee );
//                            task.onGoingWorkEntries().add( ongoingWorkEntry );
//                        }
//                    }
//                }
//
//                system.accounts().add( account );
//            }
//
//            complete( unitOfWork );
//        }
//
//        /**
//         * Creates a project, legal condition, task, and project assignee per customer.
//         */
//        private void initProjectsTasksAndAssignees()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            Iterable<Account> accounts = getAllAccounts( unitOfWork );
//
//            Calendar now = Calendar.getInstance();
//            now.add( Calendar.MONTH, -1 );
//            Date startDate = now.getTime();
//            now.add( Calendar.MONTH, 2 );
//            Date endDate = now.getTime();
//
//
//            for( Account account : accounts )
//            {
//                account = unitOfWork.dereference( account );
//
//                final Customer customer = account.customers().iterator().next();
//                ContactPerson contactPerson = customer.contactPersons().iterator().next();
//                PriceRateSchedule priceRateSchedule = customer.priceRateSchedules().iterator().next();
//                Staff staff = account.staffs().iterator().next();
//                PriceRate priceRate = priceRateSchedule.priceRates().iterator().next();
//                ProjectAssignee projectAssignee = newProjectAssignee( unitOfWork, true, staff, priceRate );
//
//                Project project = newProject( unitOfWork, "Chronos Qi4j", "Chronos v0.1", ProjectStatus.ACTIVE );
//                project.customer().set( customer );
//                project.primaryContactPerson().set( contactPerson );
//                project.contactPersons().addAll( customer.contactPersons() );
//                project.priceRateSchedule().set( priceRateSchedule );
//                project.estimateTime().set( newTimeRange( unitOfWork, startDate, endDate ) );
//                project.projectAssignees().add( projectAssignee );
//
//                for( int i = 0; i < 7; i++ )
//                {
//                    Task task = newTask( unitOfWork, "Task " + i,
//                                         "Task " + i + " description", startDate, TaskStatusEnum.OPEN );
//                    task.user().set( staff );
//                    project.tasks().add( task );
//                }
//
//                for( int i = 0; i < 7; i++ )
//                {
//                    LegalCondition condition = newLegalCondition( unitOfWork, "Maintenance contract",
//                                                                  "Maintenance contract 3 years" );
//                    project.legalConditions().add( condition );
//                }
//
//                account.projects().add( project );
//            }
//
//            complete( unitOfWork );
//        }
//
//        /**
//         * Creates a customer and a contact person for available accounts
//         */
//        private void initCustomersAndContactPersons()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//            Iterable<Account> accounts = getAllAccounts( unitOfWork );
//
//            for( Account account : accounts )
//            {
//                account = unitOfWork.dereference( account );
//                final Iterator<PriceRateSchedule> priceRateScheduleIterator = account.priceRateSchedules().iterator();
//
//                ContactPerson projectManager = newContactPerson( unitOfWork, "Michael", "Lim", "michael", "michael",
//                                                                 GenderType.MALE, "Project Manager" );
//                Contact mobile = newContact( unitOfWork, "Mobile", "7073247032" );
//                projectManager.contacts().add( mobile );
//
//                QueryBuilder<SystemRole> systemRoleQBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//                SystemRole systemRoleTemplate = templateFor( SystemRole.class );
//
//                systemRoleQBuilder.where( eq( systemRoleTemplate.name(), SystemRole.CONTACT_PERSON ) );
//
//                QueryBuilder<SystemRole> queryBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//                SystemRole contactPersonRole = queryBuilder.newQuery().find();
//
//                projectManager.systemRoles().add( contactPersonRole );
//
//                Customer customer = newCustomer( unitOfWork, "Client A", "clientA",
//                                                 "line 1", "line 2", "city", "state", "country", "41412" );
//                customer.priceRateSchedules().add( priceRateScheduleIterator.next() );
//                customer.contactPersons().add( projectManager );
//
//                account.customers().add( customer );
//
//                ContactPerson projectManager2 = newContactPerson( unitOfWork, "Yada", "Yada", "yada", "yada",
//                                                                  GenderType.MALE, "Manager" );
//                Contact mobile2 = newContact( unitOfWork, "Mobile", "7073247032" );
//                projectManager2.contacts().add( mobile2 );
//                projectManager2.systemRoles().add( contactPersonRole );
//
//                Customer customer2 = newCustomer( unitOfWork, "YadaYada", "YadaYada",
//                                                  "line 1", "line 2", "city", "state", "country", "41412" );
//                customer2.priceRateSchedules().add( priceRateScheduleIterator.next() );
//                customer2.contactPersons().add( projectManager2 );
//
//                account.customers().add( customer2 );
//            }
//
//            complete( unitOfWork );
//        }
//
//        /**
//         * Create default project roles and staff for available accounts.
//         */
//        private void initProjectRolesAndStaff()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            Iterable<Account> accounts = getAllAccounts( unitOfWork );
//
//            for( Account account : accounts )
//            {
//                // Creating and adding project roles
//                account = unitOfWork.dereference( account );
//
//                ProjectRole programmerRole = newProjectRole( unitOfWork, "Programmer" );
//                ProjectRole consultantRole = newProjectRole( unitOfWork, "Consultant" );
//                ProjectRole projectManagerRole = newProjectRole( unitOfWork, "Project Manager" );
//
//                account.projectRoles().add( programmerRole );
//                account.projectRoles().add( consultantRole );
//                account.projectRoles().add( projectManagerRole );
//
//                // Creating and adding staffs
//                Staff boss = newUser( unitOfWork, StaffEntity.class, "The", "Boss", GenderType.MALE );
//                boss.login().set( newLogin( unitOfWork, "boss", "boss" ) );
//                boss.salary().set( newMoney( unitOfWork, 8000L, "USD" ) );
//
//                Staff developer = newUser( unitOfWork, StaffEntity.class, "The", "Developer", GenderType.MALE );
//                developer.login().set( newLogin( unitOfWork, "developer", "developer" ) );
//                developer.salary().set( newMoney( unitOfWork, 2000L, "USD" ) );
//
//                SystemRole bossSystemRoleTemplate = templateFor( SystemRole.class );
//
//                QueryBuilder<SystemRole> bossSystemRoleQueryBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//                bossSystemRoleQueryBuilder.where( or(
//                    eq( bossSystemRoleTemplate.name(), ACCOUNT_ADMIN ),
//                    eq( bossSystemRoleTemplate.name(), SystemRole.STAFF )
//                ) );
//
//                for( SystemRole role : bossSystemRoleQueryBuilder.newQuery() )
//                {
//                    boss.systemRoles().add( role );
//                }
//
//                QueryBuilder<SystemRole> developerSystemRoleQueryBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//                SystemRole developerSystemRoleTemplate = templateFor( SystemRole.class );
//
//                developerSystemRoleQueryBuilder.where( or( eq( developerSystemRoleTemplate.name(),
//                                                               ACCOUNT_DEVELOPER ), eq( developerSystemRoleTemplate.name(), SystemRole.STAFF ) ) );
//
//                for( SystemRole role : developerSystemRoleQueryBuilder.newQuery() )
//                {
//                    developer.systemRoles().add( role );
//                }
//
//                account.staffs().add( boss );
//                account.staffs().add( developer );
//            }
//
//            complete( unitOfWork );
//        }
//
//        /**
//         * Creates default price rate schedule for available accounts.
//         */
//        private void initPriceRateSchedule()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            Iterable<Account> accounts = getAllAccounts( unitOfWork );
//
//            for( Account account : accounts )
//            {
//                account = unitOfWork.dereference( account );
//
//                PriceRateSchedule priceRateSchedule = newPriceRateSchedule( unitOfWork, "Default A" );
//                priceRateSchedule.currency().set( Currency.getInstance( "USD" ) );
//
//                for( ProjectRole projectRole : account.projectRoles() )
//                {
//                    PriceRate priceRate = newPriceRate( unitOfWork, 3000L, "USD", PriceRateTypeEnum.MONTHLY );
//                    priceRate.projectRole().set( projectRole );
//                    priceRateSchedule.priceRates().add( priceRate );
//                }
//                account.priceRateSchedules().add( priceRateSchedule );
//
//                PriceRateSchedule priceRateSchedule2 = newPriceRateSchedule( unitOfWork, "Default B" );
//                priceRateSchedule2.currency().set( Currency.getInstance( "EUR" ) );
//
//                for( ProjectRole projectRole : account.projectRoles() )
//                {
//                    PriceRate priceRate2 = newPriceRate( unitOfWork, 2000L, "EUR", PriceRateTypeEnum.MONTHLY );
//                    priceRate2.projectRole().set( projectRole );
//                    priceRateSchedule2.priceRates().add( priceRate2 );
//                }
//                account.priceRateSchedules().add( priceRateSchedule2 );
//            }
//
//            complete( unitOfWork );
//        }
//
//        /**
//         * Creates the default admin user.
//         */
//        private void initAdmin()
//        {
//            UnitOfWork unitOfWork = newUnitOfWork( uowf );
//
//            Login adminLogin = newLogin( unitOfWork, "admin", "admin" );
//            Admin adminUser = newUser( unitOfWork, AdminEntity.class, "System",
//                                       "Administrator", GenderType.MALE );
//            adminUser.login().set( adminLogin );
//
//            QueryBuilder<SystemRole> adminSystemRoleQueryBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//            SystemRole adminSystemRoleTemplate = templateFor( SystemRole.class );
//
//            adminSystemRoleQueryBuilder.where( eq( adminSystemRoleTemplate.name(), SystemRole.SYSTEM_ADMIN ) );
//
//            QueryBuilder<SystemRole> queryBuilder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );
//
//            for( SystemRole role : queryBuilder.newQuery() )
//            {
//                adminUser.systemRoles().add( unitOfWork.dereference( role ) );
//            }
//
//            complete( unitOfWork );
//        }
//
//
//        /**
//         * Compares a given Name composite with a text. Returns true if text equals name.
//         */
//        private boolean equals( Name name, String text )
//        {
//            return text.equals( name.name().get() );
//        }
//
//        private void complete( UnitOfWork unitOfWork )
//        {
//            try
//            {
//                unitOfWork.complete();
//            }
//            catch( UnitOfWorkCompletionException uofwce )
//            {
//                System.err.println( uofwce.getLocalizedMessage() );
//                uofwce.printStackTrace();
//
//                unitOfWork.reset();
//            }
//        }
//
//        private UnitOfWork newUnitOfWork( UnitOfWorkFactory unitOfWorkFactory )
//        {
//            return unitOfWorkFactory.newUnitOfWork();
//        }
//
//        private Login newLogin( UnitOfWork unitOfWork, String username, String password )
//        {
//
//            EntityBuilder<LoginEntity> loginBuilder = unitOfWork.newEntityBuilder( LoginEntity.class );
//            loginBuilder.stateOfComposite().name().set( username );
//            loginBuilder.stateOfComposite().password().set( password );
//            loginBuilder.stateOfComposite().isEnabled().set( true );
//
//            return loginBuilder.newInstance();
//        }
//
//        private <T extends User, K extends T> T newUser( UnitOfWork unitOfWork, Class<K> clazz,
//                                                         String firstName, String lastName, GenderType gender )
//        {
//            EntityBuilder<K> userBuilder = unitOfWork.newEntityBuilder( clazz );
//            userBuilder.stateOfComposite().firstName().set( firstName );
//            userBuilder.stateOfComposite().lastName().set( lastName );
//            userBuilder.stateOfComposite().gender().set( gender );
//
//            return userBuilder.newInstance();
//        }
//
//        private Account newAccount( String name, String reference )
//        {
//            Account account = accountFactory.newAccount( name );
//            account.accountDetail().changeReferenceName( reference );
//            return account;
//        }
//
//        private Address newAddress( UnitOfWork unitOfWork, String firstLine,
//                                    String secondLine, String cityName,
//                                    String stateName, String countryName, String zipCode )
//        {
//            EntityBuilder<AddressEntity> addressBuilder =
//                unitOfWork.newEntityBuilder( AddressEntity.class );
//            addressBuilder.stateOfComposite().firstLine().set( firstLine );
//            addressBuilder.stateOfComposite().secondLine().set( secondLine );
//            addressBuilder.stateOfComposite().zipCode().set( zipCode );
//            addressBuilder.stateOfComposite().city().set(
//                newCity( unitOfWork, cityName, newState( unitOfWork, stateName ),
//                         newCountry( unitOfWork, countryName ) ) );
//
//            return addressBuilder.newInstance();
//        }
//
//        private City newCity( UnitOfWork unitOfWork, String cityName, State state, Country country )
//        {
//            EntityBuilder<CityEntity> cityBuilder = unitOfWork.newEntityBuilder( CityEntity.class );
//            cityBuilder.stateOfComposite().name().set( cityName );
//            cityBuilder.stateOfComposite().state().set( state );
//            cityBuilder.stateOfComposite().country().set( country );
//
//            return cityBuilder.newInstance();
//        }
//
//        private State newState( UnitOfWork unitOfWork, String stateName )
//        {
//            EntityBuilder<StateEntity> stateBuilder = unitOfWork.newEntityBuilder( StateEntity.class );
//            stateBuilder.stateOfComposite().name().set( stateName );
//
//            return stateBuilder.newInstance();
//        }
//
//        private Country newCountry( UnitOfWork unitOfWork, String countryName )
//        {
//            EntityBuilder<CountryEntity> countryBuilder =
//                unitOfWork.newEntityBuilder( CountryEntity.class );
//            countryBuilder.stateOfComposite().name().set( countryName );
//
//            return countryBuilder.newInstance();
//        }
//
//        private Money newMoney( UnitOfWork unitOfWork, Long amount, String currencyCode )
//        {
//            EntityBuilder<MoneyEntity> money = unitOfWork.newEntityBuilder( MoneyEntity.class );
//            money.stateOfComposite().amount().set( amount );
//            money.stateOfComposite().currency().set( Currency.getInstance( currencyCode ) );
//
//            return money.newInstance();
//        }
//
//        private ProjectRole newProjectRole( UnitOfWork unitOfWork, String projectRoleName )
//        {
//            EntityBuilder<ProjectRoleEntity> projectRoleBuilder =
//                unitOfWork.newEntityBuilder( ProjectRoleEntity.class );
//            projectRoleBuilder.stateOfComposite().name().set( projectRoleName );
//
//            return projectRoleBuilder.newInstance();
//        }
//
//        private PriceRateSchedule newPriceRateSchedule( UnitOfWork unitOfWork, String reference )
//        {
//            EntityBuilder<PriceRateScheduleEntity> priceRateScheduleBuilder =
//                unitOfWork.newEntityBuilder( PriceRateScheduleEntity.class );
//            priceRateScheduleBuilder.stateOfComposite().name().set( reference );
//
//            return priceRateScheduleBuilder.newInstance();
//        }
//
//        private PriceRate newPriceRate( UnitOfWork unitOfWork, Long amount, String currencyCode,
//                                        PriceRateTypeEnum priceRateTypeEnum )
//        {
//            EntityBuilder<PriceRateEntity> priceRateBuilder =
//                unitOfWork.newEntityBuilder( PriceRateEntity.class );
//            priceRateBuilder.stateOfComposite().amount().set( amount );
//            priceRateBuilder.stateOfComposite().currency().set( Currency.getInstance( currencyCode ) );
//            priceRateBuilder.stateOfComposite().priceRateType().set( priceRateTypeEnum );
//
//            return priceRateBuilder.newInstance();
//        }
//
//        private Customer newCustomer( UnitOfWork unitOfWork, String customerName,
//                                      String reference, String firstLine, String secondLine,
//                                      String cityName, String stateName, String countryName, String zipCode )
//        {
//            EntityBuilder<CustomerEntity> customerBuilder =
//                unitOfWork.newEntityBuilder( CustomerEntity.class );
//            customerBuilder.stateOfComposite().name().set( customerName );
//            customerBuilder.stateOfComposite().reference().set( reference );
//            customerBuilder.stateOfComposite().isEnabled().set( true );
//            customerBuilder.stateOfComposite().address().set(
//                newAddress( unitOfWork, firstLine, secondLine, cityName, stateName, countryName, zipCode ) );
//
//            return customerBuilder.newInstance();
//        }
//
//        private ContactPerson newContactPerson( UnitOfWork unitOfWork, String firstName,
//                                                String lastName, String username, String password,
//                                                GenderType genderType, String relationshipName )
//        {
//            EntityBuilder<ContactPerson> contactPersonBuilder = unitOfWork.newEntityBuilder( ContactPerson.class );
//            ContactPerson contactPersonTempla = contactPersonBuilder.stateOfComposite();
//            contactPersonTempla.firstName().set( firstName );
//            contactPersonTempla.lastName().set( lastName );
//            contactPersonTempla.gender().set( genderType );
//            contactPersonTempla.login().set( newLogin( unitOfWork, username, password ) );
//            contactPersonTempla.relationship().set( newRelationship( unitOfWork, relationshipName ) );
//
//            return contactPersonBuilder.newInstance();
//        }
//
//        private Relationship newRelationship( UnitOfWork unitOfWork, String relationshipName )
//        {
//            EntityBuilder<RelationshipEntity> relationshipBuilder =
//                unitOfWork.newEntityBuilder( RelationshipEntity.class );
//            relationshipBuilder.stateOfComposite().relationship().set( relationshipName );
//
//            return relationshipBuilder.newInstance();
//        }
//
//        private Contact newContact( UnitOfWork unitOfWork, String contactType, String contactValue )
//        {
//            EntityBuilder<ContactEntity> contactBuilder =
//                unitOfWork.newEntityBuilder( ContactEntity.class );
//            contactBuilder.stateOfComposite().contactType().set( contactType );
//            contactBuilder.stateOfComposite().contactValue().set( contactValue );
//
//            return contactBuilder.newInstance();
//        }
//
//        private Project newProject( UnitOfWork unitOfWork, String projectName,
//                                    String formalReference, ProjectStatus projectStatus )
//        {
//            EntityBuilder<ProjectEntity> projectBuilder =
//                unitOfWork.newEntityBuilder( ProjectEntity.class );
//            projectBuilder.stateOfComposite().name().set( projectName );
//            projectBuilder.stateOfComposite().reference().set( formalReference );
//            projectBuilder.stateOfComposite().projectStatus().set( projectStatus );
//
//            return projectBuilder.newInstance();
//        }
//
//        private timeRange newTimeRange( UnitOfWork unitOfWork, Date startDate, Date endDate )
//        {
//            EntityBuilder<TimeRangeEntity> timeRangeBuilder =
//                unitOfWork.newEntityBuilder( TimeRangeEntity.class );
//            timeRangeBuilder.stateOfComposite().startTime().set( startDate );
//            timeRangeBuilder.stateOfComposite().endTime().set( endDate );
//
//            return timeRangeBuilder.newInstance();
//        }
//
//        private ProjectAssignee newProjectAssignee( UnitOfWork unitOfWork, boolean isLead,
//                                                    Staff staff, PriceRate priceRate )
//        {
//            EntityBuilder<ProjectAssigneeEntity> projectAssigneeBuilder =
//                unitOfWork.newEntityBuilder( ProjectAssigneeEntity.class );
//            projectAssigneeBuilder.stateOfComposite().isLead().set( isLead );
//            projectAssigneeBuilder.stateOfComposite().staff().set( staff );
//            projectAssigneeBuilder.stateOfComposite().priceRate().set( priceRate );
//
//            return projectAssigneeBuilder.newInstance();
//        }
//
//        private LegalCondition newLegalCondition( UnitOfWork unitOfWork, String value, String description )
//        {
//            EntityBuilder<LegalConditionEntity> legalConditionBuilder =
//                unitOfWork.newEntityBuilder( LegalConditionEntity.class );
//            legalConditionBuilder.stateOfComposite().name().set( value );
//            legalConditionBuilder.stateOfComposite().description().set( description );
//
//            return legalConditionBuilder.newInstance();
//        }
//
//        private Task newTask( UnitOfWork unitOfWork, String title, String description,
//                              Date createdDate, TaskStatusEnum taskStatus )
//        {
//            EntityBuilder<TaskEntity> taskBuilder = unitOfWork.newEntityBuilder( TaskEntity.class );
//            taskBuilder.stateOfComposite().title().set( title );
//            taskBuilder.stateOfComposite().description().set( description );
//            taskBuilder.stateOfComposite().createdDate().set( createdDate );
//            taskBuilder.stateOfComposite().taskStatus().set( taskStatus );
//
//            return taskBuilder.newInstance();
//        }
//
//        private Comment newComment( UnitOfWork unitOfWork, String comment, Date createdDate, User user )
//        {
//            EntityBuilder<CommentEntity> commentBuilder =
//                unitOfWork.newEntityBuilder( CommentEntity.class );
//            commentBuilder.stateOfComposite().text().set( comment );
//            commentBuilder.stateOfComposite().createdDate().set( createdDate );
//            commentBuilder.stateOfComposite().user().set( user );
//
//            return commentBuilder.newInstance();
//        }
//
//        private WorkEntry newWorkEntry( UnitOfWork unitOfWork, String title, String description, Date createdDate,
//                                        Date startTime, Date endTime, ProjectAssignee projectAssignee )
//        {
//            EntityBuilder<WorkEntryEntity> workEntryBuilder =
//                unitOfWork.newEntityBuilder( WorkEntryEntity.class );
//            workEntryBuilder.stateOfComposite().title().set( title );
//            workEntryBuilder.stateOfComposite().description().set( description );
//            workEntryBuilder.stateOfComposite().createdDate().set( createdDate );
//            workEntryBuilder.stateOfComposite().startTime().set( startTime );
//            workEntryBuilder.stateOfComposite().endTime().set( endTime );
//            workEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );
//
//            return workEntryBuilder.newInstance();
//        }
//
//        private OngoingWorkEntry newOngoingWorkEntry( UnitOfWork unitOfWork,
//                                                      Date createdDate, ProjectAssignee projectAssignee )
//        {
//            EntityBuilder<OngoinWorkEntryEntity> ongoingWorkEntryBuilder =
//                unitOfWork.newEntityBuilder( OngoinWorkEntryEntity.class );
//            ongoingWorkEntryBuilder.stateOfComposite().createdDate().set( createdDate );
//            ongoingWorkEntryBuilder.stateOfComposite().projectAssignee().set( projectAssignee );
//
//            return ongoingWorkEntryBuilder.newInstance();
//        }
    }
}
