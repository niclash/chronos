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
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryService;

public abstract class MockWorkEntryMiscServiceMixin implements WorkEntryService
{
    @ThisCompositeAs private WorkEntryService workEntryService;

    public List<WorkEntryEntityComposite> getRecentWorkEntryList( AccountEntityComposite account )
    {
        List<WorkEntryEntityComposite> resultList = new ArrayList<WorkEntryEntityComposite>();

        Iterator<ProjectEntityComposite> projectIter = account.projectIterator();

        while( projectIter.hasNext() )
        {
            List<WorkEntryEntityComposite> list = workEntryService.findAll( projectIter.next() );

            resultList.addAll( list );
        }

        return resultList;
    }

    public List<WorkEntryEntityComposite> getRecentWorkEntryList( AccountEntityComposite account, FindFilter findFilter )
    {
        return getRecentWorkEntryList( account ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countAll( AccountEntityComposite account )
    {
        Iterator<ProjectEntityComposite> projectIter = account.projectIterator();

        int total = 0;

        while( projectIter.hasNext() )
        {
            total += workEntryService.countAll( projectIter.next() );
        }

        return total;
    }

    public void deleteWorkEntry( WorkEntryEntityComposite entryEntityComposite )
    {
        //TODO
    }

    public List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project )
    {
        Iterator<TaskEntityComposite> iterator = project.taskIteraotr();

        List<WorkEntryEntityComposite> resultList = new ArrayList<WorkEntryEntityComposite>();

        while( iterator.hasNext() )
        {
            Iterator<TaskAssigneeEntityComposite> taskAssigneeIter = iterator.next().taskAssigneeIterator();

            while( taskAssigneeIter.hasNext() )
            {
                resultList.addAll( workEntryService.findAll( taskAssigneeIter.next() ) );
            }
        }

        return resultList;
    }


    public List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project, FindFilter findFilter )
    {
        return findAll( project ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countAll( ProjectEntityComposite project )
    {
        return findAll( project ).size();
    }

}
