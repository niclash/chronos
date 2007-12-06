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
import java.util.UUID;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import static org.qi4j.composite.PropertyValue.property;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.Identity;

public class MockWorkEntryServiceMixin implements WorkEntryService
{
    @Structure CompositeBuilderFactory factory;

    public WorkEntryEntityComposite newInstance( Class<? extends WorkEntryEntityComposite> clazz )
    {
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = UUID.randomUUID().toString();

        compositeBuilder.properties( Identity.class, property( "identity", uid ) );

        return (WorkEntryEntityComposite) compositeBuilder.newInstance();
    }

    public List<WorkEntryEntityComposite> findAll( HasWorkEntries hasWorkEntries )
    {
        List<WorkEntryEntityComposite> list = new ArrayList<WorkEntryEntityComposite>();

        for( Iterator<WorkEntryEntityComposite> iter = hasWorkEntries.workEntryIterator(); iter.hasNext(); )
        {
            list.add( iter.next() );
        }

        return list;
    }

    public List<WorkEntryEntityComposite> findAll( HasWorkEntries hasWorkEntries, FindFilter findFilter )
    {
        return findAll( hasWorkEntries ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countAll( HasWorkEntries hasWorkEntries )
    {
        return findAll( hasWorkEntries ).size();
    }

    public void update( WorkEntryEntityComposite workEntry )
    {
        //nothing
    }

    public WorkEntryEntityComposite get( HasWorkEntries hasWorkEntries, String id )
    {
        for( Iterator<WorkEntryEntityComposite> iter = hasWorkEntries.workEntryIterator(); iter.hasNext(); )
        {
            WorkEntryEntityComposite workEntry = iter.next();

            if( workEntry.getIdentity().equals( id ) )
            {
                return workEntry;
            }
        }
        return null;
    }

    public void delete( HasWorkEntries hasWorkEntries, List<WorkEntryEntityComposite> workEntries )
    {
        for( WorkEntryEntityComposite workEntry : workEntries )
        {
            hasWorkEntries.removeWorkEntry( workEntry );
        }
    }

    public List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project, StaffEntityComposite staff )
    {
        List<WorkEntryEntityComposite> resultList = new ArrayList<WorkEntryEntityComposite>();

        Iterator<WorkEntryEntityComposite> iter = project.workEntryIterator();

        addWorkEntryList( iter, resultList, staff );

        Iterator<TaskEntityComposite> taskIter = project.taskIteraotr();

        while( taskIter.hasNext() )
        {
            addWorkEntryList( taskIter.next().workEntryIterator(), resultList, staff );
        }

        return resultList;
    }

    public List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project, StaffEntityComposite staff, FindFilter findFilter )
    {
        return findAll( project, staff ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    private void addWorkEntryList( Iterator<WorkEntryEntityComposite> iter, List<WorkEntryEntityComposite> resultList, StaffEntityComposite staff )
    {
        while( iter.hasNext() )
        {
            WorkEntryEntityComposite workEntry = iter.next();

            if( workEntry.getProjectAssignee().getStaff().getIdentity().equals( staff.getIdentity() ) )
            {
                resultList.add( workEntry );
            }
        }
    }

    public int countAll( ProjectEntityComposite project, StaffEntityComposite staff )
    {
        return findAll( project, staff ).size();
    }
}
