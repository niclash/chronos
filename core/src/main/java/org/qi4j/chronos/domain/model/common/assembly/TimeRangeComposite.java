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
import org.qi4j.chronos.domain.model.common.timeRange.TimeRangeState;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.Optional;
import org.qi4j.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 */
@Mixins( TimeRangeComposite.TimeRangeMixin.class )
interface TimeRangeComposite extends TimeRange, Composite
{
    class TimeRangeMixin
        implements TimeRange
    {
        @This private TimeRangeState state;

        public final Date startTime()
        {
            return state.startTime().get();
        }

        public final Date endTime()
        {
            return state.endTime().get();
        }

        public final void updateTimeRange( @Optional Date startTime, @Optional Date endTime )
        {
            state.startTime().set( startTime );
            state.endTime().set( endTime );
        }
    }
}
