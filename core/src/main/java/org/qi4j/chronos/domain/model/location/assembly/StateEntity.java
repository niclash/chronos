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
package org.qi4j.chronos.domain.model.location.assembly;

import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityState;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.State;
import org.qi4j.chronos.domain.model.location.country.StateState;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( StateEntity.StateMixin.class )
interface StateEntity extends State, EntityComposite
{
    abstract class StateMixin
        implements State
    {
        @This private StateState state;

        @Structure private UnitOfWorkFactory uowf;
        @Structure private QueryBuilderFactory qbf;
        @This private StateEntity meAsEntity;

        public final Country country()
        {
            return state.country().get();
        }

        public final Query<City> cities()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<City> builder = qbf.newQueryBuilder( City.class );
            builder.where( eq( templateFor( CityState.class ).state(), meAsEntity ) );
            return builder.newQuery( uow );
        }
    }
}
