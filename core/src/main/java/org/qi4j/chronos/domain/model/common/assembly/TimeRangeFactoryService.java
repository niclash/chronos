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
import org.qi4j.chronos.domain.model.common.timeRange.TimeRange;
import org.qi4j.chronos.domain.model.common.timeRange.TimeRangeFactory;
import org.qi4j.chronos.domain.model.common.timeRange.TimeRangeState;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.Optional;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( TimeRangeFactoryService.TimeRangeFactoryMixin.class )
interface TimeRangeFactoryService extends TimeRangeFactory, ServiceComposite
{
    class TimeRangeFactoryMixin
        implements TimeRangeFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final TimeRange create( @Optional Date startTime, @Optional Date endTime )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<TimeRange> builder = uow.newEntityBuilder( TimeRange.class );
            TimeRangeState state = builder.stateFor( TimeRangeState.class );
            state.startTime().set( startTime );
            state.endTime().set( endTime );
            return builder.newInstance();
        }
    }
}
