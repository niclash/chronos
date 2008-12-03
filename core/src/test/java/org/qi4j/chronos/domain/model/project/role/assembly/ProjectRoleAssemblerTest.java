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

import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.chronos.domain.model.project.AbstractProjectTest;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleFactory;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleRepository;
import org.qi4j.entity.NoSuchEntityException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.service.ServiceFinder;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class ProjectRoleAssemblerTest extends AbstractProjectTest
{
    @Test
    public final void servicesAvailabilityTest()
    {
        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        assertNotNull( serviceFinder.findService( ProjectRoleFactory.class ) );
        assertNotNull( serviceFinder.findService( ProjectRoleRepository.class ) );
    }

    @Test
    public final void entityAvailabilityTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();
        try
        {
            uow.newEntityBuilder( ProjectRole.class );
        }
        catch( NoSuchEntityException e )
        {
            fail( ProjectRole.class.toString() + " must be available as entity." );
        }
        finally
        {
            uow.discard();
        }
    }
}
