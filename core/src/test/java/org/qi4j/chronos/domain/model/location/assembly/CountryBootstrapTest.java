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
import org.junit.Test;
import static org.qi4j.chronos.domain.model.location.assembly.CountryBootstrap.CountryBootstrapMixin.*;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryCode;
import org.qi4j.chronos.domain.model.location.country.CountryRepository;
import org.qi4j.service.ServiceReference;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class CountryBootstrapTest extends AbstractLocationTest
{
    @Test
    public void testBootstrap()
    {
        ServiceReference<CountryRepository> repositoryReference =
            moduleInstance.serviceFinder().findService( CountryRepository.class );
        CountryRepository countryRepository = repositoryReference.get();

        try
        {
            String[][] data = DATA;
            for( String[] entries : data )
            {
                String countryCodeNumeric = entries[ 1 ];

                Country country = countryRepository.findByNumericCode( countryCodeNumeric );
                String countryName = entries[ 0 ];

                assertNotNull( "Country [" + countryName + "] is not found.", country );
                assertEquals( countryName, country.name() );

                CountryCode countryCode = country.countryCode();
                assertEquals( countryCodeNumeric, countryCode.numeric() );
                assertEquals( entries[ 3 ], countryCode.alpha2() );
                assertEquals( entries[ 2 ], countryCode.alpha3() );
            }
        }
        finally
        {
            repositoryReference.releaseService();
        }
    }
}
