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

import java.util.Date;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.period.PeriodFactory;
import org.qi4j.chronos.domain.model.common.period.PeriodState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.common.Optional;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( PeriodFactoryService.PeriodFactoryMixin.class )
interface PeriodFactoryService extends PeriodFactory, ServiceComposite
{
    class PeriodFactoryMixin
        implements PeriodFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Period create( @Optional Date startTime, @Optional Date endTime )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<Period> builder = uow.newEntityBuilder( Period.class );
            PeriodState state = builder.stateFor( PeriodState.class );
            state.startTime().set( startTime );
            state.endTime().set( endTime );
            return builder.newInstance();
        }
    }
}
