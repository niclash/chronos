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
import org.qi4j.service.ServiceLocator;
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
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.library.general.model.GenderType;
import java.util.Currency;

final class DummyDataInitializer
{
    private @Structure ServiceLocator serviceLocator;

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

        initAdmin();
        initStaff();

        unitOfWork = complete( unitOfWork, unitOfWorkFactory );
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
        Staff boss = newUser( unitOfWork, StaffEntityComposite.class, "The", "Boss", GenderType.MALE );
        boss.login().set( newLogin( unitOfWork, "boss", "boss" ) );
        boss.salary().set( newMoney( unitOfWork, 8000L, "EUR" ) );

        Staff developer = newUser( unitOfWork, StaffEntityComposite.class, "The", "Developer", GenderType.MALE );
        developer.login().set( newLogin( unitOfWork, "developer", "developer" ) );
        developer.salary().set( newMoney( unitOfWork, 2000L, "USD" ) );

        for( SystemRole role : roleService.findAllStaffSystemRole() )
        {
            boss.systemRoles().add( role );
            if( role.name().equals( SystemRole.ACCOUNT_DEVELOPER ) )
            {
                developer.systemRoles().add( role );
            }
        }

        for( Account account : accountService.findAll() )
        {
            account.staffs().add( boss );
            account.staffs().add( developer );
        }
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
}
