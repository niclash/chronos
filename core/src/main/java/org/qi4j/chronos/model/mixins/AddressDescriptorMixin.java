/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.ValidatableAddressEntityComposite;
import org.qi4j.library.general.model.City;
import org.qi4j.library.general.model.Country;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.State;

/**
 * Provides implementation example to display an validatableAddress. Different countries have different ways of displaying validatableAddress.
 * For example,
 * <p/>
 * Albertinkatu 36 B
 * 00180 HELSINKI
 * FINLAND
 * <p/>
 * and
 * <p/>
 * 202 King St.
 * Vic 3000
 * Australia
 */
public final class AddressDescriptorMixin implements Descriptor
{
    private static final String EMPTY_STRING = "";
    private static final String SINGLE_SPACE = " ";
    private static final String NEW_LINE = "\n";

    @Uses private ValidatableAddressEntityComposite validatableAddress;

    public String getDisplayValue()
    {
        StringBuilder displayName = new StringBuilder();

        String firstLine = validatableAddress.getFirstLine();
        appendValueTo( firstLine, displayName );

        String secondLine = validatableAddress.getSecondLine();
        appendValueTo( secondLine, displayName );

        String thirdLine = validatableAddress.getThirdLine();
        appendValueTo( thirdLine, displayName );

        String zipCode = validatableAddress.getZipCode();
        appendValueTo( zipCode, displayName );

        City city = validatableAddress.getCity();
        if( city != null )
        {
            String cityName = city.getName();

            if( isNotEmptyString( cityName ) )
            {
                if( isNotEmptyString( zipCode ) )
                {
                    displayName.append( SINGLE_SPACE );
                }
                else if( displayName.length() > 0 )
                {
                    displayName.append( NEW_LINE );
                }
                displayName.append( cityName );
            }

            State state = city.getState();
            if( state != null )
            {
                String stateName = state.getName();
                appendValueTo( stateName, displayName );
            }

            Country country = city.getCountry();
            String countryName = country.getName();
            appendValueTo( countryName, displayName );
        }

        return displayName.toString();
    }

    private void appendValueTo( String value, StringBuilder appendTo )
    {
        if( isNotEmptyString( value ) )
        {
            if( appendTo.length() > 0 )
            {
                appendTo.append( NEW_LINE );
            }
            appendTo.append( value );
        }
    }

    private boolean isNotEmptyString( String value )
    {
        return value != null && !EMPTY_STRING.equals( value );
    }
}
