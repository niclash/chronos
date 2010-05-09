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

import org.junit.Test;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.chronos.domain.model.project.AbstractProjectTest;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleExistsException;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleFactory;

import static org.junit.Assert.*;

public class ProjectRoleFactoryTest
    extends AbstractProjectTest
{
    @Test
    public void createTest()
        throws Throwable
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<ProjectRoleFactory> factoryRef = serviceFinder.findService( ProjectRoleFactory.class );
        ProjectRoleFactory factory = factoryRef.get();

        ProjectRole role = factory.create( "Project Manager" );
        assertNotNull( role );
        uow.complete();
        uow = unitOfWorkFactory.newUnitOfWork();
        try
        {
            factory.create( "Project Manager" );
            fail( "Creating role with the same name must fail." );
        }
        catch( ProjectRoleExistsException e )
        {
            // Expected
            role = uow.get( role );
            uow.remove( role );
            uow.complete();
        }
        finally
        {
            if( uow.isOpen() )
            {
                uow.discard();
            }
        }
    }
}
