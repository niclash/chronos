package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressPersistentComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;

public final class AddressNotNullValidationModifier implements Validatable
{
    @Uses private AddressPersistentComposite address;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        String cityName = address.getCityName();
        String countryName = address.getCountryName();

        if( isEmptyString( cityName ) || isEmptyString( countryName ) )
        {
            throw new ValidationException( "City name and country name must be populated.");
        }

        next.validate();
    }

    private boolean isEmptyString( String value )
    {
        return value == null || "".equals( value );
    }
}
