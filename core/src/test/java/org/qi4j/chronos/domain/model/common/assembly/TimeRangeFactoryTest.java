/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.common.assembly;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.chronos.domain.model.common.timeRange.TimeRange;
import org.qi4j.chronos.domain.model.common.timeRange.TimeRangeFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.service.ServiceFinder;
import org.qi4j.service.ServiceReference;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class TimeRangeFactoryTest extends AbstractCommonTest
{
    @Test
    public final void createTimeRangeTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<TimeRangeFactory> factoryRef = serviceFinder.findService( TimeRangeFactory.class );
        TimeRangeFactory timeRangeFactory = factoryRef.get();
        try
        {
            Date now = new Date();
            Date oneHourLater = new Date( now.getTime() + 3600000 );
            TimeRange timeRange = timeRangeFactory.create( now, oneHourLater );

            assertEquals( now, timeRange.startTime() );
            assertEquals( oneHourLater, timeRange.endTime() );
        }
        finally
        {
            factoryRef.releaseService();
            uow.discard();
        }
    }
}
