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
package org.qi4j.chronos.ui.account;

import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public class AccountDataProvider extends AbstractSortableDataProvider<AccountEntityComposite>
{
    public String getId( AccountEntityComposite accountEntityComposite )
    {
        return accountEntityComposite.getIdentity();
    }

    public AccountEntityComposite load( String id )
    {
        return getAccountService().get( id );
    }

    public List<AccountEntityComposite> dataList( int first, int count )
    {
        return getAccountService().find( new FindFilter( first, count ) );
    }

    public int size()
    {
        return getAccountService().countAll();
    }

    protected AccountService getAccountService()
    {
        return ChronosWebApp.getServices().getAccountService();
    }

}
