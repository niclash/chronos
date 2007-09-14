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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.PriceRateScheduleService;

public class MockPriceRateScheduleServiceMixin implements PriceRateScheduleService
{
    public List<PriceRateSchedule> findAll( HasPriceRateSchedules hasPriceRateSchedules, FindFilter findFilter )
    {
        return findAll( hasPriceRateSchedules ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<PriceRateSchedule> findAll( HasPriceRateSchedules hasPriceRateSchedules )
    {
        final List<PriceRateSchedule> list = new ArrayList<PriceRateSchedule>();

        loopPriceRateSchedule( hasPriceRateSchedules, new LoopCallBack()
        {
            public boolean callBack( PriceRateScheduleComposite priceRateSchedule )
            {
                list.add( priceRateSchedule );

                return true;
            }
        } );

        return list;
    }

    private void loopPriceRateSchedule( HasPriceRateSchedules hasPriceRateSchedules, LoopCallBack loopCallBack )
    {
        Iterator<PriceRateScheduleComposite> iter = hasPriceRateSchedules.priceRateScheduleIterator();

        while( iter.hasNext() )
        {
            boolean next = loopCallBack.callBack( iter.next() );

            if( !next )
            {
                break;
            }
        }
    }

    private interface LoopCallBack
    {
        boolean callBack( PriceRateScheduleComposite priceRateSchedule );
    }

    public int countAll( HasPriceRateSchedules hasPriceRateSchedules )
    {
        return findAll( hasPriceRateSchedules ).size();
    }

    public PriceRateScheduleComposite get( HasPriceRateSchedules hasPriceRateSchedules, final String priceRateName )
    {
        final PriceRateScheduleComposite[] returnValue = new PriceRateScheduleComposite[1];

        loopPriceRateSchedule( hasPriceRateSchedules, new LoopCallBack()
        {
            public boolean callBack( PriceRateScheduleComposite priceRateSchedule )
            {
                if( priceRateSchedule.getName().equals( priceRateName ) )
                {
                    returnValue[ 0 ] = priceRateSchedule;
                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }

    public void update( PriceRateSchedule priceRateSchedule )
    {
        //nothing to do here
    }
}