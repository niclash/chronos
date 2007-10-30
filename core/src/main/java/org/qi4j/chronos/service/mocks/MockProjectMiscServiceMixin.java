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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.qi4j.annotation.scope.ThisCompositeAs;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;

public abstract class MockProjectMiscServiceMixin implements ProjectService
{
    @ThisCompositeAs private ProjectService projectService;

    public List<ProjectEntityComposite> getRecentProjects( AccountEntityComposite account, FindFilter findFilter )
    {
        return projectService.findAll( account, findFilter );
    }

    public List<ProjectEntityComposite> findAll( ContactPersonEntityComposite contactPerson, FindFilter findFilter )
    {
        return findAll( contactPerson ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ProjectEntityComposite> findAll( final StaffEntityComposite staff )
    {
        final Set<ProjectEntityComposite> resultList = new HashSet<ProjectEntityComposite>();

        loopAllProject( new LoopCallBack<ProjectEntityComposite>()
        {
            public boolean callBack( ProjectEntityComposite project )
            {
                Iterator<ProjectAssigneeEntityComposite> projectAssigneeIter = project.projectAssigneeIterator();

                while( projectAssigneeIter.hasNext() )
                {
                    if( projectAssigneeIter.next().getStaff().getIdentity().equals( staff.getIdentity() ) )
                    {
                        resultList.add( project );

                        break;
                    }
                }

                return false;
            }
        } );


        return new ArrayList<ProjectEntityComposite>( resultList );
    }

    public List<ProjectEntityComposite> findAll( final ContactPersonEntityComposite contactPerson )
    {
        final Set<ProjectEntityComposite> resultList = new HashSet<ProjectEntityComposite>();

        loopAllProject( new LoopCallBack<ProjectEntityComposite>()
        {
            public boolean callBack( ProjectEntityComposite project )
            {
                if( project.getPrimaryContactPerson() != null )
                {
                    if( isSameContactPerson( project.getPrimaryContactPerson(), contactPerson ) )
                    {
                        resultList.add( project );
                    }
                }

                Iterator<ContactPersonEntityComposite> contactPersonIter = project.contactPersonIterator();

                while( contactPersonIter.hasNext() )
                {
                    if( isSameContactPerson( contactPersonIter.next(), contactPerson ) )
                    {
                        resultList.add( project );
                    }
                }

                return false;
            }
        } );


        return new ArrayList<ProjectEntityComposite>( resultList );
    }

    private boolean isSameContactPerson( ContactPersonEntityComposite contactPerson1, ContactPersonEntityComposite contactPerson2 )
    {
        return contactPerson1.getIdentity().equals( contactPerson2.getIdentity() );
    }

    public int countAll( ContactPersonEntityComposite contactPerson )
    {
        return findAll( contactPerson ).size();
    }

    private void loopAllProject( LoopCallBack<ProjectEntityComposite> loopCallBack )
    {
        List<ProjectEntityComposite> allList = projectService.findAll();

        for( ProjectEntityComposite project : allList )
        {
            loopCallBack.callBack( project );
        }
    }

    public List<ProjectEntityComposite> findAll( StaffEntityComposite staff, FindFilter findFilter )
    {
        return findAll( staff ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countAll( StaffEntityComposite staff )
    {
        return findAll( staff ).size();
    }

    public List<ProjectEntityComposite> getRecentProjects( StaffEntityComposite staff )
    {
        //bp. temp solution.
        return findAll( staff );
    }

    public List<ProjectEntityComposite> getRecentProjects( StaffEntityComposite staff, FindFilter findFilter )
    {
        //bp. temp solution.
        return findAll( staff, findFilter );
    }

    public int countRecentProject( StaffEntityComposite staff )
    {
        //bp. temp solution.
        return countAll( staff );
    }

    public int countRecentProject( AccountEntityComposite account )
    {
        //bp. temp solution
        return projectService.countAll( account );
    }

    public ProjectEntityComposite getProjectByTask( TaskEntityComposite task )
    {
        List<ProjectEntityComposite> projects = projectService.findAll();

        for( ProjectEntityComposite project : projects )
        {
            Iterator<TaskEntityComposite> taskIter = project.taskIteraotr();

            while( taskIter.hasNext() )
            {
                if( taskIter.next().getIdentity().equals( task.getIdentity() ) )
                {
                    return project;
                }
            }
        }

        return null;
    }

    public void changeProjectStatus( ProjectStatus projectStatus, Collection<ProjectEntityComposite> projects )
    {
        for( ProjectEntityComposite project : projects )
        {
            project.setProjectStatus( projectStatus );

            projectService.update( project );
        }
    }

    public int countAll( AccountEntityComposite account, ProjectStatus projectStatus )
    {
        List<ProjectEntityComposite> projects = projectService.findAll( account );

        int total = 0;

        for( ProjectEntityComposite project : projects )
        {
            if( project.getProjectStatus() == projectStatus )
            {
                total++;
            }
        }

        return total;
    }
}
