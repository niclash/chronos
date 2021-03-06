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

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalConditionFactory;
import org.qi4j.chronos.domain.model.common.legalCondition.LegalConditionState;

@Mixins( LegalConditionFactoryService.LegalConditionFactoryMixin.class )
interface LegalConditionFactoryService extends LegalConditionFactory, ServiceComposite
{
    public class LegalConditionFactoryMixin
        implements LegalConditionFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public LegalCondition create( String legalConditionName )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            EntityBuilder<LegalCondition> builder = uow.newEntityBuilder( LegalCondition.class );
            LegalConditionState state = builder.instanceFor( LegalConditionState.class );
            state.name().set( legalConditionName );
            return builder.newInstance();
        }
    }
}
