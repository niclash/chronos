package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.CompositeRepository;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressPersistentComposite;
import org.qi4j.chronos.model.composites.CityPersistentComposite;
import org.qi4j.chronos.model.composites.CountryPersistentComposite;
import org.qi4j.chronos.model.composites.StatePersistentComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;

public final class CityStateCountryValidationModifier implements Validatable
{
    @Uses private AddressPersistentComposite address;
    @Dependency private CompositeRepository repository;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        String cityName = address.getCityName();
        String countryName = address.getCountryName();
        String stateName = address.getStateName();

        // TODO: replace with query for city with using the specified CityName
        CityPersistentComposite cityPersistentComposite = repository.getInstance( cityName, CityPersistentComposite.class );

        if( cityPersistentComposite != null )
        {
            CountryPersistentComposite country = cityPersistentComposite.getCountry();
            String otherCountryName = country.getCountryName();

            if( isEqualIgnoreCase( countryName, otherCountryName ) )
            {
                if( isNotEmptyString( stateName ) )
                {
                    StatePersistentComposite state = cityPersistentComposite.getState();

                    if( state != null )
                    {
                        String otherStateName = state.getStateName();
                        if( isEqualIgnoreCase( stateName, otherStateName ) )
                        {
                            next.validate();
                        }
                        else
                        {
                            throw new ValidationException( "City [" + cityName + "] in country [" + countryName +
                                                           "] is not located in state [" + stateName +
                                                           "]. It is located in [" + otherStateName + "]" );
                        }

                    }
                    else
                    {
                        throw new ValidationException( "City [" + cityName + "] does not have a state." );
                    }
                }

            }
            else
            {
                throw new ValidationException( "City [" + cityName +
                                               "] is not located in country [" + countryName +
                                               "]. It should be located in [" + otherCountryName + "]" );
            }
        }
        else
        {
            throw new ValidationException( "City [" + cityName + "] does not exist." );
        }
    }

    private boolean isNotEmptyString( String value )
    {
        return value != null && !"".equals( value );
    }

    private boolean isEqualIgnoreCase( String value, String otherValue )
    {
        if( value != null && otherValue != null )
        {
            value = value.toLowerCase();
            otherValue = otherValue.toLowerCase();
            return value.equals( otherValue );
        }

        return false;
    }
}
