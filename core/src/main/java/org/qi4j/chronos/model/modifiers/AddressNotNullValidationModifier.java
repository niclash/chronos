package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;

/**
 * Making sure that all required fields are populated and not-null before
 * address is persisted.
 * <p/>
 * Required fields for Address are:
 * <li>
 * <ol>City
 * <ol>Country
 * </li>
 */
public final class AddressNotNullValidationModifier implements Validatable
{
    @Uses private AddressEntityComposite address;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        CityComposite city = address.getCity();
        CountryComposite country = address.getCountry();

        if( city == null || country == null )
        {
            throw new ValidationException( "City name and country for address must be populated." );
        }

        next.validate();
    }
}
