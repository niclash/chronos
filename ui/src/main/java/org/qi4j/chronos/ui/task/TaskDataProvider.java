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
package org.qi4j.chronos.ui.task;

import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class TaskDataProvider extends AbstractSortableDataProvider<Task, String>
{
    public String getId( Task task )
    {
        return ( (TaskEntityComposite) task).identity().get();
    }

    public Task load( String s )
    {
        return ChronosWebApp.getServices().getTaskService().get( s );
    }
}
