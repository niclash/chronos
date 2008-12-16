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
package org.qi4j.chronos.domain.model.common.task.mixins;

import java.util.Date;
import org.qi4j.chronos.domain.model.common.task.TaskStatus;
import org.qi4j.chronos.domain.model.common.task.WorkEntry;
import org.qi4j.chronos.domain.model.common.task.WorkEntryState;
import org.qi4j.api.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public abstract class WorkEntryMixin
    implements WorkEntry
{
    @This private WorkEntryState state;

    public final Date createdDate()
    {
        return state.createdDate().get();
    }

    public final String summary()
    {
        return state.summary().get();
    }

    public final String description()
    {
        return state.description().get();
    }

    public TaskStatus status()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date lastUpdatedDate()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
