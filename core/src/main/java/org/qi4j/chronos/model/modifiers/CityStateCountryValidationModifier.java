package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.EntityRepository;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.AddressPersistentComposite;
import org.qi4j.chronos.model.composites.CityPersistentComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public final class CityStateCountryValidationModifier implements Validatable
{
    @Uses private AddressPersistentComposite address;
    @Dependency private EntityRepository repository;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        CityComposite city = address.getCity();
        String cityId = city.getIdentity();

        CityPersistentComposite cityPersistentComposite = repository.getInstance( cityId, CityPersistentComposite.class );

        if( cityPersistentComposite != null )
        {
            CountryComposite country = address.getCountry();
            String countryId = country.getIdentity();

            CountryComposite otherCountry = cityPersistentComposite.getCountry();
            String otherCountryId = otherCountry.getIdentity();

            if( countryId.equals( otherCountryId ) )
            {
                StateComposite state = address.getState();
                if( state != null )
                {
                    StateComposite otherState = cityPersistentComposite.getState();
                    if( otherState != null )
                    {
                        String stateId = state.getIdentity();
                        String otherStateId = otherState.getIdentity();
                        
                        if( stateId.equals( otherStateId ) )
                        {
                            next.validate();
                        }
                        else
                        {
                            throw new ValidationException( "City with id [" + cityId +
                                                           "] doesn't match with state with id [" + stateId +
                                                           "]. It matches with state with id [" + stateId + "]" );
                        }

                    }
                    else
                    {
                        throw new ValidationException( "City with id [" + cityId + "] does not have state." );
                    }
                }

            }
            else
            {
                throw new ValidationException( "City with id [" + cityId +
                                               "] doesn't match with country with id [" + countryId +
                                               "]. It matches to country with id [" + otherCountryId + "]" );
            }
        }
        else
        {
            throw new ValidationException( "City with id [" + cityId + "] does not exist." );
        }
    }
}
