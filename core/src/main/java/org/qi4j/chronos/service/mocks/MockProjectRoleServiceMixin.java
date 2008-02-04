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
import java.util.List;
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;

public class MockProjectRoleServiceMixin implements ProjectRoleService
{
    @Structure private CompositeBuilderFactory factory;

    public List<ProjectRoleComposite> findAll( AccountEntityComposite account, FindFilter findFilter )
    {
        return findAll( account ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ProjectRoleComposite> findAll( AccountEntityComposite account )
    {
        List<ProjectRoleComposite> copyProjectRoles = new ArrayList<ProjectRoleComposite>();
        SetAssociation<ProjectRoleComposite> projectRoles = account.projectRoles();
        for( ProjectRoleComposite projectRoleComposite : projectRoles )
        {
            copyProjectRoles.add( CloneUtil.cloneProjectRole( factory, projectRoleComposite ) );
        }

        return copyProjectRoles;
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
            if( projectRole.name().get().equals( projectRoleName ) )
            {
                return projectRole;
            }
        }

        return null;
    }

    public void updateProjectRole( AccountEntityComposite account, ProjectRoleComposite oldProjectRole, ProjectRoleComposite updatedProjectRole )
    {
        ProjectRoleComposite originalOld = get_0( account, oldProjectRole.name().get() );

        SetAssociation<ProjectRoleComposite> projectRoles = account.projectRoles();
        projectRoles.remove( originalOld );
        projectRoles.add( updatedProjectRole );
    }

    public void deleteProjectRole( AccountEntityComposite account, Collection<ProjectRoleComposite> projectRoles )
    {
        SetAssociation<ProjectRoleComposite> accountProjectRoles = account.projectRoles();
        for( ProjectRoleComposite projectRole : projectRoles )
        {
            ProjectRoleComposite toBeDeleted = null;
            for( ProjectRoleComposite accountProjectRole : accountProjectRoles )
            {

                if( accountProjectRole.name().get().equals( projectRole.name().get() ) )
                {
                    toBeDeleted = accountProjectRole;
                    break;
                }
            }

            if( toBeDeleted != null )
            {
                accountProjectRoles.remove( toBeDeleted );
            }
        }
    }
}
