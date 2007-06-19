package org.qi4j.chronos.model;

import org.qi4j.api.annotation.Uses;

public class LocationDescriptor implements Descriptor
{
    private static final String EMPTY_STRING = "";

    @Uses
    private Location location;
    private static final String NEW_LINE = "\n";
    private static final String EMPTY_SPACE = " ";

    public String getDisplayName()
    {
        StringBuilder displayName = new StringBuilder();

        String address = location.getAddress();
        appendValueTo( address, displayName );

        String zipCode = location.getZipCode();
        appendValueTo( zipCode, displayName );

        String city = location.getCityName();

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


        String state = location.getStateName();
        appendValueTo( state, displayName );

        String country = location.getCountryName();
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
