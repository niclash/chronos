/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.PriceRateSchedule;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public final class HasPriceRateSchedulesMixin implements HasPriceRateSchedules
{
    private final List<PriceRateSchedule> list;

    public HasPriceRateSchedulesMixin()
    {
        list = new ArrayList<PriceRateSchedule>( );
    }

    public void addPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        list.add( priceRateSchedule );
    }

    public void removePriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        list.remove( priceRateSchedule );
    }

    public Iterator<PriceRateSchedule> priceRateScheduleIterator()
    {
        return list.iterator();
    }
}
