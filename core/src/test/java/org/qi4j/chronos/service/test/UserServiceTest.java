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
package org.qi4j.chronos.service.test;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.test.AbstractCommonTest;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.service.user.UserServiceComposite;
import org.qi4j.chronos.service.user.UserServiceConfiguration;
import org.qi4j.library.general.model.GenderType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 2:32:39 AM
 */
public class UserServiceTest extends AbstractCommonTest
{
    private UserService userService;

    protected Account account;

    protected List<Customer> customers;

    @Before @Override public void setUp() throws Exception
    {
        super.setUp();

        userService = serviceLocator.lookupService( UserServiceComposite.class ).get();
    }

    @After @Override public void tearDown() throws Exception
    {
        userService = null;
        account = null;
        customers = null;
        
        super.tearDown();
    }

    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        super.assemble( module );
        module.addComposites( UserServiceConfiguration.class );
        module.addServices( UserServiceComposite.class );
    }

    @Test public void initServiceTest() throws Exception
    {
        assertNotNull( "UserService is null!!!", userService );

        initAdmin();
        unitOfWork = complete( unitOfWork );

        assertNotNull( "There should be at least 1 admin user!!!", userService.getAdmin( "admin", "admin" ) );
    }

    @Test public void getTest() throws Exception
    {
        initData();
        String bossId = null;
        for( User user : account.staffs() )
        {
            if( user.fullName().get().endsWith( "oss" ) )
            {
                bossId = user.identity().get();
            }
        }
        unitOfWork  = complete( unitOfWork );

        User find = userService.get( unitOfWork, bossId );
        assertTrue( "Cannot find boss!!!!", find.fullName().get().equals( "Boss, The" ));
    }

    @Test public void getUserTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        account = unitOfWork.dereference( account );
        User boss = userService.getUser( account, "boss" );
        assertNotNull( "Unable to find User Boss!!!", boss );
        assertEquals( "User boss is not found !!!", "Boss, The", boss.fullName().get() );
        assertNotNull( "Salary is null!!!", ((Staff) boss).salary().get() );

        System.out.println( ( (Staff) boss).salary().get().displayValue().get() );

        User contactPerson = userService.getUser( account, "contact2c3" );
        assertNotNull( "Unable to find contact person!!!", contactPerson );
        assertEquals( "User contact person 2 from customer 3 is not found !!!", "Contact, Person 2", contactPerson.fullName().get() );
    }

    @Test public void getAdminTest() throws Exception
    {
        initAdmin();

        unitOfWork = complete( unitOfWork );

        User admin = userService.getAdmin( "admin", "admin" );
        assertEquals( "Admin user is not found!!!", "System, Administrator", admin.fullName().get() );
    }

    @Test public void hasThisSystemRoleTest() throws Exception
    {
        initAdmin();
        initData();

        unitOfWork = complete( unitOfWork );

        User admin = userService.getAdmin( "admin", "admin" );
        assertTrue( "Admin user does not have System Admin role!!!", userService.hasThisSystemRole( admin, SystemRole.SYSTEM_ADMIN ) );
        assertTrue( "Admin user does not have Account Admin role!!!", userService.hasThisSystemRole( admin, SystemRole.ACCOUNT_ADMIN ) );
    }

    @Test public void isUniqueTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        account = unitOfWork.dereference( account );
        assertTrue( "Username is unique", userService.isUnique( account, "wakaka" ));
    }

    @Test public void deleteStaffTest() throws Exception
    {
        initData();

        int noOfStaffs = account.staffs().size();

        unitOfWork = complete( unitOfWork );

        account = unitOfWork.dereference( account );
        Staff deletedStaff = (Staff) userService.getUser( account,  "developer1" );

        assertNotNull( "Unable to find staff!!!", deletedStaff );

        account.staffs().remove( deletedStaff );
        unitOfWork.remove( deletedStaff );

        unitOfWork = complete( unitOfWork );

        account = unitOfWork.dereference( account);
        unitOfWork.refresh( account );

        assertNotSame( "Unable to delete staff!!!! NotExpected[" + noOfStaffs +
                       "] Actual[" + account.staffs().size() + "]", noOfStaffs, account.staffs().size() );
    }

    private void initAdmin()
    {
        Admin admin = unitOfWork.newEntityBuilder( AdminEntityComposite.class ).newInstance();
        admin.firstName().set( "Administrator" );
        admin.lastName().set( "System" );
        admin.gender().set( GenderType.MALE );

        admin.login().set( newLogin( "admin", "admin" ) );

        admin.systemRoles().add( newSystemRole( SystemRoleTypeEnum.ADMIN, SystemRole.SYSTEM_ADMIN ) );
        admin.systemRoles().add( newSystemRole( SystemRoleTypeEnum.CONTACT_PERSON, SystemRole.CONTACT_PERSON ) );
        admin.systemRoles().add( newSystemRole( SystemRoleTypeEnum.STAFF, SystemRole.ACCOUNT_ADMIN ) );
        admin.systemRoles().add( newSystemRole( SystemRoleTypeEnum.STAFF, SystemRole.ACCOUNT_DEVELOPER ) );

        userService.addAdmin( admin );
    }

    private void initData()
    {
        account = newAccount( "Test Corp", "Ref: 1234" );
        account.staffs().addAll( getStaffs() );

        customers = getCustomers();

        account.customers().addAll( customers );
    }
}
