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
package org.qi4j.chronos.ui.account;

import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.account.AccountService;
import org.apache.wicket.Session;
import java.util.List;

public class AccountDataProvider extends AbstractSortableDataProvider<Account, String>
{
    private AccountService getAccountService()
    {
        return ( ( ChronosSession ) Session.get() ).getAccountService();
    }

    public int getSize()
    {
        return getAccountService().count();
    }

    public String getId( Account account )
    {
        return getAccountService().getId( account );
    }

    public Account load( String accountId )
    {
        return getAccountService().get( accountId );
    }

    public List<Account> dataList( int first, int count )
    {
        return getAccountService().findAll( first, count );
    }
}
