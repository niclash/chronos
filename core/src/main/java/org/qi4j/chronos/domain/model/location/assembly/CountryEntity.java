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

import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityState;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryCode;
import org.qi4j.chronos.domain.model.location.country.CountryState;
import org.qi4j.chronos.domain.model.location.country.State;
import org.qi4j.chronos.domain.model.location.country.StateState;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.AggregateEntity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import static org.qi4j.query.QueryExpressions.*;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CountryEntity.CountryMixin.class )
interface CountryEntity extends Country, Name, AggregateEntity
{
    abstract class CountryMixin
        implements Country
    {
        @This private CountryState state;
        @Structure private CompositeBuilderFactory cbf;
        private CountryCode countryCode;

        @Structure private UnitOfWorkFactory uowf;
        @This private CountryEntity meAsEntity;

        public final CountryCode countryCode()
        {
            if( countryCode == null )
            {
                CompositeBuilder<CountryCode> builder = cbf.newCompositeBuilder( CountryCode.class );
                builder.use( state );
                countryCode = builder.newInstance();
            }

            return countryCode;
        }

        public final Query<State> states()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<State> builder = uow.queryBuilderFactory().newQueryBuilder( State.class );
            builder.where( eq( templateFor( StateState.class ).country(), meAsEntity ) );
            return builder.newQuery();
        }

        public final Query<City> cities()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<City> builder = uow.queryBuilderFactory().newQueryBuilder( City.class );
            builder.where( eq( templateFor( CityState.class ).country(), meAsEntity ) );
            return builder.newQuery();
        }
    }
}
