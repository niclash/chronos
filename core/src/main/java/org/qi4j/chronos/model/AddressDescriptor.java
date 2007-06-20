package org.qi4j.chronos.model;

import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressComposite;

public final class AddressDescriptor implements Descriptor
{
    private static final String EMPTY_STRING = "";

    @Uses
    private AddressComposite address;
    private static final String NEW_LINE = "\n";
    private static final String EMPTY_SPACE = " ";

    public String getDisplayName()
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

        String city = address.getCityName();

        if( isNotEmptyString( city ) )
        {
            if( isNotEmptyString( zipCode ) )
            {
                displayName.append( EMPTY_SPACE );
            }
            else if( displayName.length() > 0 )
            {
                displayName.append( NEW_LINE );
            }
            displayName.append( city );
        }


        String state = address.getStateName();
        appendValueTo( state, displayName );

        String country = address.getCountryName();
        appendValueTo( country, displayName );

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
