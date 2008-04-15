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

import org.qi4j.chronos.test.AbstractCommonTest;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.chronos.service.account.AccountServiceConfiguration;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 7:11:50 PM
 */
public class AccountServiceTest extends AbstractCommonTest
{
    private AccountService accountService;

    private Account primary;

    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        super.assemble( module );

        module.addServices( AccountServiceComposite.class );
        module.addComposites( AccountServiceConfiguration.class );
    }

    @Before @Override public void setUp() throws Exception
    {
        super.setUp();

        accountService = serviceLocator.lookupService( AccountServiceComposite.class ).get();
    }

    @After @Override public void tearDown() throws Exception
    {
        accountService = null;
        primary = null;

        super.setUp();
    }

    @Test public void getIdTest() throws Exception
    {
        primary = newAccount( "Primary Account", "primary" );

        unitOfWork = complete( unitOfWork );

        primary = unitOfWork.dereference( primary );
        assertNotNull( "Account Id is nul!!!", accountService.getId( primary ) );
    }

    @Test public void getTest() throws Exception
    {
        primary = newAccount( "Primary Account", "primary" );
        String primaryId = accountService.getId( primary );

        unitOfWork = complete( unitOfWork );

        Account find = accountService.get( unitOfWork,  primaryId );
        primary = unitOfWork.dereference( primary );

        assertEquals( "Account reference is not the same!!!", primary.reference().get(), find.reference().get() );
    }

    @Test public void addTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        assertEquals( "No of accounts not equals to 3!!!", 3, accountService.count() );

        // trying to add same account multiple times
        primary = unitOfWork.dereference( primary );

        accountService.add( primary );
        accountService.add( primary );
        accountService.add( primary );

        unitOfWork = complete( unitOfWork );

        assertEquals( "Same account has been added multiple times!!!!", 3, accountService.count()  );
    }

    @Test public void removeTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        assertEquals( "No of accounts not equals to 3!!!", 3, accountService.count() );

        primary = unitOfWork.dereference( primary );
        String primaryId = accountService.getId( primary );
        accountService.remove( primary );

        unitOfWork = complete( unitOfWork );

        try
        {
            primary = unitOfWork.find( primaryId, AccountEntityComposite.class );
            fail( "Should not been able to find deleted account" );
        }
        catch( EntityCompositeNotFoundException ecnfe )
        {
            unitOfWork.refresh();
        }

        assertNull( "Deleted account is not null!!!", accountService.get( unitOfWork, primaryId ) );
    }

    @Test public void findAllTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        assertTrue( "There should be 3 accounts!!!", accountService.count() == 3 );

        for( Account account : accountService.findAll() )
        {
            assertTrue( "Account name has changed!!!", account.name().get().endsWith( " Account" ) );
            print( account.name().get() );
        }
    }

    @Test public void findAccountByNameTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        Account secondary = accountService.findAccountByName( "Secondary Account" );

        assertEquals( "Secondary account reference is not equal!!!", "secondary", secondary.reference().get() );
    }

    @Test public void isUniqueTest() throws Exception
    {
        initData();

        unitOfWork = complete( unitOfWork );

        assertTrue( "This account name should be unique because it is nonexistent!!!", accountService.isUnique( "wakaka" ) );
    }

    @Test public void countTest() throws Exception
    {
        unitOfWork = complete( unitOfWork );

        assertEquals( "There should not be zero accounts!!! ", 0, accountService.count() );

        initData();

        unitOfWork = complete( unitOfWork );

        assertEquals( "There should be exactly three accounts!!! ", 3, accountService.count() );
    }

    private void initData()
    {
        primary = newAccount( "Primary Account", "primary" );
        Account secondary = newAccount( "Secondary Account", "secondary" );
        Account tertiary = newAccount( "Tertiary Account", "tertiary" );

        accountService.add( primary );
        accountService.add( secondary );
        accountService.add( tertiary );
    }
}
