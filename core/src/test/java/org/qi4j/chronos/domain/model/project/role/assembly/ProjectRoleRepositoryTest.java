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
package org.qi4j.chronos.domain.model.project.role.assembly;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.chronos.domain.model.project.AbstractProjectTest;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleExistsException;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleFactory;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleId;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleRepository;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class ProjectRoleRepositoryTest extends AbstractProjectTest
{
    private static final String PROJECT_MANAGER = "Project Manager";
    private static final String QA = "QA";
    private static final String[] ROLES = { PROJECT_MANAGER, QA };

    @Test
    public void repositoryTest()
        throws Throwable
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        bootstrapSomeData( uow );
        testFind();
        removeAllRoles( uow );
    }

    private void bootstrapSomeData( UnitOfWork uow )
        throws ProjectRoleExistsException, UnitOfWorkCompletionException
    {
        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<ProjectRoleFactory> factoryRef = serviceFinder.findService( ProjectRoleFactory.class );

        try
        {
            ProjectRoleFactory roleFactory = factoryRef.get();
            roleFactory.create( PROJECT_MANAGER );
            roleFactory.create( QA );
            uow.apply();
        }
        finally
        {
            factoryRef.releaseService();
        }
    }

    private void testFind()
    {
        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<ProjectRoleRepository> repositoryRef = serviceFinder.findService( ProjectRoleRepository.class );
        ProjectRoleRepository repository = repositoryRef.get();

        try
        {
            Query<ProjectRole> allProjectRoles = repository.findAll();
            assertEquals( 2, allProjectRoles.count() );
            for( ProjectRole projectRole : allProjectRoles )
            {
                String roleName = projectRole.name();
                assertTrue( Arrays.binarySearch( ROLES, roleName ) >= 0 );
                ProjectRoleId id = projectRole.projectRoleId();

                assertNotNull( repository.find( id ) );
            }
        }
        finally
        {
            repositoryRef.releaseService();
        }
    }

    private void removeAllRoles( UnitOfWork uow )
        throws UnitOfWorkCompletionException
    {
        QueryBuilderFactory qbf = uow.queryBuilderFactory();
        QueryBuilder<ProjectRole> builder = qbf.newQueryBuilder( ProjectRole.class );
        Query<ProjectRole> roleQuery = builder.newQuery();

        try
        {
            for( ProjectRole role : roleQuery )
            {
                uow.remove( role );
            }
        }
        finally
        {
            uow.complete();
        }
    }
}
