package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.ValidatableAddressEntityComposite;
import org.qi4j.library.general.model.City;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;

/**
 * Making sure that all required fields are populated and not-null before
 * validatableAddress is persisted.
 * <p/>
 * Required fields for Address are:
 * <li>
 * <ol>City
 * <ol>Country
 * </li>
 */
public final class AddressNotNullValidationModifier implements Validatable
{
    @Uses private ValidatableAddressEntityComposite validatableAddress;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        City city = validatableAddress.getCity();
        if( city == null )
        {
            throw new ValidationException( "City for address must be populated." );
        }

        next.validate();
    }
}
