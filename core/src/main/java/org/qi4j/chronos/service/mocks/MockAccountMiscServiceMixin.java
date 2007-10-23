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

import java.util.Iterator;
import java.util.List;
import org.qi4j.api.annotation.scope.ThisAs;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.AccountService;

public abstract class MockAccountMiscServiceMixin implements AccountService
{
    @ThisAs private AccountService accountService;

    public MockAccountMiscServiceMixin()
    {

    }

    public AccountEntityComposite getAccount( StaffEntityComposite target )
    {
        List<AccountEntityComposite> accountList = accountService.findAll();

        for( AccountEntityComposite account : accountList )
        {
            Iterator<StaffEntityComposite> staffIterator = account.staffIterator();

            while( staffIterator.hasNext() )
            {
                StaffEntityComposite staff = staffIterator.next();

                if( staff.getIdentity().equals( target.getIdentity() ) )
                {
                    return account;
                }
            }
        }

        return null;
    }

    public AccountEntityComposite getAccount( ContactPersonEntityComposite contactPerson )
    {
        //TODO bp. fixme.
        return null;
    }

    public AccountEntityComposite getAccountByProject( ProjectEntityComposite project )
    {
        List<AccountEntityComposite> accounts = accountService.findAll();

        for( AccountEntityComposite account : accounts )
        {
            Iterator<ProjectEntityComposite> projectIter = account.projectIterator();

            while( projectIter.hasNext() )
            {
                if( projectIter.next().getIdentity().equals( project.getIdentity() ) )
                {
                    return account;
                }
            }
        }

        return null;
    }

}
