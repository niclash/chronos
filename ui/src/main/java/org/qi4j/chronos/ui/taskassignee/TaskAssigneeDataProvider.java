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
package org.qi4j.chronos.ui.taskassignee;

import java.util.List;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.TaskAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class TaskAssigneeDataProvider extends AbstractSortableDataProvider<TaskAssigneeEntityComposite, String>
{
    private TaskAssigneeService getTaskAssigneeService()
    {
        return ChronosWebApp.getServices().getTaskAssigneeService();
    }

    public int getSize()
    {
        return getTaskAssigneeService().countAll( getTask() );
    }

    public String getId( TaskAssigneeEntityComposite t )
    {
        return t.getIdentity();
    }

    public TaskAssigneeEntityComposite load( String s )
    {
        return getTaskAssigneeService().get( s );
    }

    public List<TaskAssigneeEntityComposite> dataList( int first, int count )
    {
        return getTaskAssigneeService().findAll( getTask(), new FindFilter( first, count ) );
    }

    public abstract TaskEntityComposite getTask();
}
