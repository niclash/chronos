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
package org.qi4j.chronos.ui.pricerate;

import java.util.List;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public class PriceRateScheduleDataProvider extends AbstractSortableDataProvider<PriceRateSchedule>
{
    private String accountId;

    public PriceRateScheduleDataProvider( String accountId )
    {
        this.accountId = accountId;
    }

    public String getId( PriceRateSchedule priceRateSchedule )
    {
        return priceRateSchedule.getName();
    }

    private AccountService getAccountService()
    {
        return ChronosWebApp.getServices().getAccountService();
    }

    public PriceRateSchedule load( String id )
    {
        return getAccountService().getPriceRateSchedule( accountId, id );
    }

    public List<PriceRateSchedule> dataList( int first, int count )
    {
        Account account = getAccountService().get( accountId );

        account.priceRateScheduleIterator();

        return getAccountService().findPriceRateSchedule( accountId, first, count );
    }

    public int size()
    {
        return getAccountService().countTotalPriceRateSchedule( accountId );
    }
}