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

import org.qi4j.chronos.domain.model.common.name.NameState;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityFactory;
import org.qi4j.chronos.domain.model.location.city.CityState;
import org.qi4j.chronos.domain.model.location.city.DuplicateCityException;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.State;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.common.Optional;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import static org.qi4j.api.query.QueryExpressions.and;
import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;
import static org.qi4j.api.query.QueryExpressions.variable;
import org.qi4j.api.query.grammar.Conjunction;
import org.qi4j.api.query.grammar.EqualsPredicate;
import org.qi4j.api.query.grammar.VariableValueExpression;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CityFactoryService.CityFactoryMixin.class )
interface CityFactoryService extends CityFactory, ServiceComposite
{
    class CityFactoryMixin
        implements CityFactory
    {
        private static final String VARIABLE_STATE = "state";
        private static final String VARIABLE_COUNTRY = "country";
        private static final String VARIABLE_CITY_NAME = "cityName";

        private static Conjunction cityWithinState;
        private static Conjunction cityWithinCountry;

        static
        {
            VariableValueExpression<State> stateVariable = variable( VARIABLE_STATE );
            CityState stateTemplate = templateFor( CityState.class );
            EqualsPredicate<State> statePredicate = eq( stateTemplate.state(), stateVariable );
            VariableValueExpression<String> cityNameVariable = variable( VARIABLE_CITY_NAME );
            EqualsPredicate<String> cityNamePredicate = eq( templateFor( NameState.class ).name(), cityNameVariable );
            cityWithinState = and( statePredicate, cityNamePredicate );

            VariableValueExpression<Country> countryVariable = variable( VARIABLE_COUNTRY );
            EqualsPredicate<Country> countryPredicate = eq( stateTemplate.country(), countryVariable );
            cityWithinCountry = and( countryPredicate, cityNamePredicate );
        }

        @Structure private UnitOfWorkFactory uowf;
        @Structure private QueryBuilderFactory qbf;

        public final City create( String cityName, @Optional State state, @Optional Country country )
            throws DuplicateCityException
        {
            validateArguments( state, country );

            UnitOfWork uow = uowf.currentUnitOfWork();
            if( state != null )
            {
                validateCityWithinState( cityName, state );
                return createCity( cityName, state, country, uow );
            }
            else
            {
                validateCityWithinCountry( cityName, country );
                return createCity( cityName, state, country, uow );
            }
        }

        private void validateArguments( State state, Country country )
        {
            if( state == null && country == null )
            {
                throw new IllegalArgumentException( "Both [state] and [country] must not be [null]." );
            }
            if( state != null && country != null )
            {
                throw new IllegalArgumentException(
                    "If [state] is [null], [country] must not be [null] or [country] is [null], [state] must not be [null]."
                );
            }
        }

        private void validateCityWithinState( String cityName, State state )
            throws DuplicateCityException
        {
            Query<City> cities = state.cities();
            QueryBuilder<City> builder = qbf.newQueryBuilder( City.class );
            builder.where( cityWithinState );
            Query<City> query = builder.newQuery( cities );
            query.setVariable( VARIABLE_STATE, state );
            query.setVariable( VARIABLE_CITY_NAME, cityName );

            City city = query.find();
            if( city != null )
            {
                throw new DuplicateCityException();
            }
        }

        private City createCity( String cityName, State state, Country country, UnitOfWork uow )
        {
            EntityBuilder<City> cityBuilder = uow.newEntityBuilder( City.class );
            CityState cityState = cityBuilder.prototypeFor( CityState.class );
            if( state != null )
            {
                cityState.state().set( state );
            }
            else
            {
                cityState.country().set( country );
            }
            cityState.name().set( cityName );
            return cityBuilder.newInstance();
        }

        private void validateCityWithinCountry( String cityName, Country country )
            throws DuplicateCityException
        {
            Query<City> cities = country.cities();
            QueryBuilder<City> builder = qbf.newQueryBuilder( City.class );
            builder.where( cityWithinCountry );
            Query<City> query = builder.newQuery( cities );
            query.setVariable( VARIABLE_COUNTRY, country );
            query.setVariable( VARIABLE_CITY_NAME, cityName );

            City city = query.find();
            if( city != null )
            {
                throw new DuplicateCityException();
            }
        }
    }
}
