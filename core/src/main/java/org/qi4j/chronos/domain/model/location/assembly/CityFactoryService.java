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
import org.qi4j.composite.Mixins;
import org.qi4j.composite.Optional;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import static org.qi4j.query.QueryExpressions.*;
import org.qi4j.query.grammar.Conjunction;
import org.qi4j.query.grammar.EqualsPredicate;
import org.qi4j.query.grammar.VariableValueExpression;
import org.qi4j.service.ServiceComposite;

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

        public final City create( String cityName, @Optional State state, @Optional Country country )
            throws DuplicateCityException
        {
            validateArguments( state, country );

            UnitOfWork uow = uowf.nestedUnitOfWork();
            if( state != null )
            {
                validateCityWithinState( cityName, state, uow );
                return createCity( cityName, state, country, uow );
            }
            else
            {
                validateCityWithinCountry( cityName, country, uow );
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

        private void validateCityWithinState( String cityName, State state, UnitOfWork uow )
            throws DuplicateCityException
        {
            Query<City> cities = state.cities();
            QueryBuilder<City> builder = uow.queryBuilderFactory().newQueryBuilder( City.class );
            builder.where( cityWithinState );
            Query<City> query = builder.newQuery( cities );
            query.setVariable( VARIABLE_STATE, state );
            query.setVariable( VARIABLE_CITY_NAME, cityName );

            City city = query.find();
            if( city != null )
            {
                uow.discard();
                throw new DuplicateCityException();
            }
        }

        private City createCity( String cityName, State state, Country country, UnitOfWork uow )
        {
            EntityBuilder<City> cityBuilder = uow.newEntityBuilder( City.class );
            CityState cityState = cityBuilder.stateFor( CityState.class );
            if( state != null )
            {
                cityState.state().set( state );
            }
            else
            {
                cityState.country().set( country );
            }

            NameState nameState = cityBuilder.stateFor( NameState.class );
            nameState.name().set( cityName );
            City city = cityBuilder.newInstance();

            try
            {
                uow.completeAndContinue();
            }
            catch( UnitOfWorkCompletionException e )
            {
                // Shouldn't happened.
                e.printStackTrace(); // TODO
            }

            return city;
        }

        private void validateCityWithinCountry( String cityName, Country country, UnitOfWork uow )
            throws DuplicateCityException
        {
            Query<City> cities = country.cities();
            QueryBuilder<City> builder = uow.queryBuilderFactory().newQueryBuilder( City.class );
            builder.where( cityWithinCountry );
            Query<City> query = builder.newQuery( cities );
            query.setVariable( VARIABLE_COUNTRY, country );
            query.setVariable( VARIABLE_CITY_NAME, cityName );

            City city = query.find();
            if( city != null )
            {
                uow.discard();
                throw new DuplicateCityException();
            }
        }
    }
}