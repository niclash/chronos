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
import java.util.Iterator;
import java.util.List;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.scope.Qi4j;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectRoleService;

public class MockProjectRoleServiceMixin implements ProjectRoleService
{
    @Qi4j private CompositeBuilderFactory factory;

    public List<ProjectRoleComposite> findAll( AccountEntityComposite account, FindFilter findFilter )
    {
        return findAll( account ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ProjectRoleComposite> findAll( AccountEntityComposite account )
    {
        List<ProjectRoleComposite> projectRoles = new ArrayList<ProjectRoleComposite>();

        Iterator<ProjectRoleComposite> projectRoleIter = account.projectRoleIterator();

        while( projectRoleIter.hasNext() )
        {
            projectRoles.add( CloneUtil.cloneProjectRole( factory, projectRoleIter.next() ) );
        }

        return projectRoles;
    }

    public int countAll( AccountEntityComposite account )
    {
        return findAll( account ).size();
    }

    public ProjectRoleComposite get( AccountEntityComposite account, String projectRoleName )
    {
        ProjectRoleComposite projectRole = get_0( account, projectRoleName );

        if( projectRole != null )
        {
            return CloneUtil.cloneProjectRole( factory, projectRole );
        }

        return null;
    }

    private ProjectRoleComposite get_0( AccountEntityComposite account, String projectRoleName )
    {
        List<ProjectRoleComposite> projectRoles = findAll( account );

        for( ProjectRoleComposite projectRole : projectRoles )
        {
            if( projectRole.getName().equals( projectRoleName ) )
            {
                return projectRole;
            }
        }

        return null;
    }

    public void updateProjectRole( AccountEntityComposite account, ProjectRoleComposite oldProjectRole, ProjectRoleComposite updatedProjectRole )
    {
        ProjectRoleComposite originalOld = get_0( account, oldProjectRole.getName() );

        account.removeProjectRole( originalOld );

        account.addProjectRole( updatedProjectRole );
    }

    public void deleteProjectRole( AccountEntityComposite account, Collection<ProjectRoleComposite> projectRoles )
    {
        for( ProjectRoleComposite projectRole : projectRoles )
        {
            ProjectRoleComposite toBeDeleted = null;
            Iterator<ProjectRoleComposite> projectRoleIter = account.projectRoleIterator();

            while( projectRoleIter.hasNext() )
            {
                ProjectRoleComposite temp = projectRoleIter.next();

                if( temp.getName().equals( projectRole.getName() ) )
                {
                    toBeDeleted = temp;
                }
            }

            if( toBeDeleted != null )
            {
                account.removeProjectRole( toBeDeleted );
            }
        }
    }
}
