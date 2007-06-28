package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressPersistentComposite;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.StateComposite;
import org.qi4j.library.general.model.composites.CountryComposite;

/**
 * Provides implementation example to display an address. Different countries have different ways of displaying address.
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

    @Uses private AddressPersistentComposite address;

    public String getDisplayValue()
    {
        StringBuilder displayName = new StringBuilder();

        String firstLine = address.getFirstLine();
        appendValueTo( firstLine, displayName );

        String secondLine = address.getSecondLine();
        appendValueTo( secondLine, displayName );

        String thirdLine = address.getThirdLine();
        appendValueTo( thirdLine, displayName );

        String zipCode = address.getZipCode();
        appendValueTo( zipCode, displayName );

        CityComposite city = address.getCity();
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
        }


        StateComposite state = address.getState();
        String stateName = state.getName();
        appendValueTo( stateName, displayName );

        CountryComposite country = address.getCountry();
        String countryName = country.getName();
        appendValueTo( countryName, displayName );

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
