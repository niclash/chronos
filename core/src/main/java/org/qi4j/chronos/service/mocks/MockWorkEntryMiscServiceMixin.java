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
import org.qi4j.api.annotation.scope.ThisAs;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryMiscService;
import org.qi4j.chronos.service.WorkEntryService;

public class MockWorkEntryMiscServiceMixin implements WorkEntryMiscService
{
    @ThisAs private WorkEntryService workEntryService;

    public List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project )
    {
        Iterator<ProjectAssigneeEntityComposite> iterator = project.projectAssigneeIterator();

        List<WorkEntryEntityComposite> resultList = new ArrayList<WorkEntryEntityComposite>();

        while( iterator.hasNext() )
        {
            resultList.addAll( workEntryService.findAll( iterator.next() ) );
//            ProjectAssigneeEntityComposite projectAssignee = iterator.next();
//
//            Iterator<WorkEntryEntityComposite> workEntryIter = projectAssignee.workEntryIterator();
//
//            while( workEntryIter.hasNext() )
//            {
//                resultList.add( workEntryIter.next() );
//            }
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
