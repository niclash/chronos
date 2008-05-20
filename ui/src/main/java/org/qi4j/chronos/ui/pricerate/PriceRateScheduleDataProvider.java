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
import java.util.ArrayList;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class PriceRateScheduleDataProvider<T extends HasPriceRateSchedules> extends AbstractSortableDataProvider<PriceRateSchedule, String>
{
    public String getId( PriceRateSchedule priceRateSchedule )
    {
        return priceRateSchedule.name().get();
    }

    public PriceRateSchedule load( String id )
    {
        return getPriceRateScheduleService().get( getHasPriceRateSchedules(), id );
    }

    private PriceRateScheduleService getPriceRateScheduleService()
    {
//        return ChronosWebApp.getServices().getPriceRateScheduleService();
        return null;
    }

    public List<PriceRateSchedule> dataList( int first, int count )
    {
        // TODO migrate
//        return getPriceRateScheduleService().findAll( getHasPriceRateSchedules(), new FindFilter( first, count ) );
        return new ArrayList<PriceRateSchedule>( getHasPriceRateSchedules().priceRateSchedules() );
    }

    public int getSize()
    {
        // TODO migrate
//        return getPriceRateScheduleService().countAll( getHasPriceRateSchedules() );
        return getHasPriceRateSchedules().priceRateSchedules().size();
    }

    public abstract T getHasPriceRateSchedules();
}
