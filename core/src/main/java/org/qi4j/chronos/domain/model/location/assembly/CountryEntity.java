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

import org.qi4j.api.composite.TransientBuilder;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import static org.qi4j.api.query.QueryExpressions.*;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityState;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryCode;
import org.qi4j.chronos.domain.model.location.country.CountryState;
import org.qi4j.chronos.domain.model.location.country.State;
import org.qi4j.chronos.domain.model.location.country.StateState;

@Mixins( CountryEntity.CountryMixin.class )
interface CountryEntity extends Country, Name, EntityComposite
{
    abstract class CountryMixin
        implements Country
    {
        @This private CountryState state;
        @Structure private TransientBuilderFactory cbf;
        @Structure private QueryBuilderFactory qbf;
        private CountryCode countryCode;

        @Structure private UnitOfWorkFactory uowf;
        @This private CountryEntity meAsEntity;

        public CountryCode countryCode()
        {
            if( countryCode == null )
            {
                TransientBuilder<CountryCode> builder = cbf.newTransientBuilder( CountryCode.class );
                builder.use( state );
                countryCode = builder.newInstance();
            }

            return countryCode;
        }

        public Query<State> states()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<State> builder = qbf.newQueryBuilder( State.class );
            builder.where( eq( templateFor( StateState.class ).country(), meAsEntity ) );
            return builder.newQuery( uow );
        }

        public Query<City> cities()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<City> builder = qbf.newQueryBuilder( City.class );
            builder.where( eq( templateFor( CityState.class ).country(), meAsEntity ) );
            return builder.newQuery( uow );
        }
    }
}
