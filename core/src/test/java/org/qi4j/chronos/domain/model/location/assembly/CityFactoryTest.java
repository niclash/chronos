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

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityFactory;
import org.qi4j.chronos.domain.model.location.city.DuplicateCityException;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryRepository;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;

public final class CityFactoryTest extends AbstractLocationTest
{
    @Test
    @Ignore( "Association querying is not implement. Waiting for QI-67 to be resolved.")
    public final void testCreate()
    {
        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<CountryRepository> countryRepositoryRef = serviceFinder.findService( CountryRepository.class );
        CountryRepository countryRepository = countryRepositoryRef.get();
        ServiceReference<CityFactory> cityFactoryRef = serviceFinder.findService( CityFactory.class );
        CityFactory cityFactory = cityFactoryRef.get();

        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();
        try
        {
            // Singapore
            Country singaporeCountry = countryRepository.findByNumericCode( "702" );
            assertNotNull( singaporeCountry );
            try
            {
                City singaporeCity = cityFactory.create( "singapore", null, singaporeCountry );
                assertNotNull( singaporeCity );
                assertEquals( "singapore", singaporeCity.name() );
            }
            catch( DuplicateCityException e )
            {
                fail( "Must not fail." );
            }

            try
            {
                cityFactory.create( "singapore", null, singaporeCountry );
                fail( "Must fail." );
            }
            catch( DuplicateCityException e )
            {
                // Expected
            }
        }
        finally
        {
            uow.discard();
        }
    }
}
