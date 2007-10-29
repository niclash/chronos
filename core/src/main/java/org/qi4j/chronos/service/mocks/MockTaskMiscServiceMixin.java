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
import org.qi4j.annotation.scope.ThisCompositeAs;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.TaskService;

public abstract class MockTaskMiscServiceMixin implements TaskService
{
    @ThisCompositeAs private TaskService taskService;

    public List<TaskEntityComposite> getRecentTasks( AccountEntityComposite account )
    {
        List<TaskEntityComposite> resultList = new ArrayList<TaskEntityComposite>();

        Iterator<ProjectEntityComposite> projectIterator = account.projectIterator();

        while( projectIterator.hasNext() )
        {
            resultList.addAll( taskService.findAll( projectIterator.next() ) );
        }

        return resultList;
    }

    public List<TaskEntityComposite> getRecentTasks( AccountEntityComposite account, FindFilter findFilter )
    {
        return getRecentTasks( account ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countRecentTasks( AccountEntityComposite account )
    {
        return getRecentTasks( account ).size();
    }

    public List<TaskEntityComposite> getRecentTasks( StaffEntityComposite staff )
    {
        List<TaskEntityComposite> resultList = new ArrayList<TaskEntityComposite>();

        List<TaskEntityComposite> allTasks = taskService.findAll();

        for( TaskEntityComposite task : allTasks )
        {
            Iterator<TaskAssigneeEntityComposite> taskAssigneeIter = task.taskAssigneeIterator();

            while( taskAssigneeIter.hasNext() )
            {
                if( taskAssigneeIter.next().getProjectAssignee().getStaff().getIdentity().equals( staff.getIdentity() ) )
                {
                    resultList.add( task );
                }
            }
        }

        return resultList;
    }

    public List<TaskEntityComposite> getRecentTasks( StaffEntityComposite staff, FindFilter findFilter )
    {
        return getRecentTasks( staff ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countRecentTasks( StaffEntityComposite staff )
    {
        return getRecentTasks( staff ).size();
    }

    public TaskEntityComposite getTaskByTaskAssignee( TaskAssigneeEntityComposite taskAssignee )
    {
        List<TaskEntityComposite> tasks = taskService.findAll();

        for( TaskEntityComposite task : tasks )
        {
            Iterator<TaskAssigneeEntityComposite> taskAssigneeIter = task.taskAssigneeIterator();

            while( taskAssigneeIter.hasNext() )
            {
                if( taskAssigneeIter.next().getIdentity().equals( taskAssignee.getIdentity() ) )
                {
                    return task;
                }
            }
        }

        return null;
    }
}
