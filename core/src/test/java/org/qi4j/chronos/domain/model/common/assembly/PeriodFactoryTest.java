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
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.period.PeriodFactory;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;

public final class PeriodFactoryTest extends AbstractCommonTest
{
    @Test
    public final void createTimeRangeTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<PeriodFactory> factoryRef = serviceFinder.findService( PeriodFactory.class );
        PeriodFactory periodFactory = factoryRef.get();
        try
        {
            Date now = new Date();
            Date oneHourLater = new Date( now.getTime() + 3600000 );
            Period period = periodFactory.create( now, oneHourLater );

            assertEquals( now, period.startTime() );
            assertEquals( oneHourLater, period.endTime() );
        }
        finally
        {
            uow.discard();
        }
    }
}
