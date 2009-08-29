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
package org.qi4j.chronos.domain.model.common.assembly;

import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalConditionFactory;

public class LegalConditionFactoryTest extends AbstractCommonTest
{
    @Test
    public void createLegalConditionTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<LegalConditionFactory> factoryRef = serviceFinder.findService( LegalConditionFactory.class );
        LegalConditionFactory factory = factoryRef.get();
        try
        {
            LegalCondition condition = factory.create( "Service Agreement" );
            assertNotNull( condition );

            assertEquals( "Service Agreement", condition.name() );

            assertNull( condition.description() );
            condition.updateDescription( "description" );
            assertEquals( "description", condition.description() );
        }
        finally
        {
            uow.discard();
        }
    }
}
