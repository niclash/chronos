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

import java.util.Collection;
import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.composite.scope.ThisCompositeAs;
import org.qi4j.entity.association.SetAssociation;

public abstract class MockAccountMiscServiceMixin implements AccountService
{
    @ThisCompositeAs private AccountService accountService;

    public MockAccountMiscServiceMixin()
    {

    }

    public AccountEntityComposite getAccount( StaffEntityComposite target )
    {
        List<AccountEntityComposite> accountList = accountService.findAll();

        for( AccountEntityComposite account : accountList )
        {
            SetAssociation<StaffEntityComposite> accountStaffs = account.staffs();
            for( StaffEntityComposite staff : accountStaffs )
            {
                if( staff.equals( target ) )
                {
                    return account;
                }
            }
        }

        return null;
    }

    public void enableAccount( boolean enabled, Collection<AccountEntityComposite> accounts )
    {
        for( AccountEntityComposite account : accounts )
        {
            account.isEnabled().set( enabled );

            accountService.update( account );
        }
    }

    public AccountEntityComposite findAccountByName( String accountName )
    {
        List<AccountEntityComposite> accounts = accountService.findAll();

        for( AccountEntityComposite account : accounts )
        {
            if( account.name().get().equalsIgnoreCase( accountName ) )
            {
                return account;
            }
        }

        return null;
    }
}
