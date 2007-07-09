package org.qi4j.chronos.model.modifiers;

import org.qi4j.api.EntityRepository;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.composites.ValidatableAddressEntityComposite;
import org.qi4j.chronos.model.composites.ChronosCityEntityComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.composites.CityEntityComposite;
import org.qi4j.library.general.model.composites.CountryEntityComposite;
import org.qi4j.library.general.model.composites.StateEntityComposite;

public final class CityStateCountryValidationModifier implements Validatable
{
    @Uses private ValidatableAddressEntityComposite validatableAddress;
    @Dependency private EntityRepository repository;
    @Modifies private Validatable next;

    public void validate() throws ValidationException
    {
        CityEntityComposite city = (CityEntityComposite) validatableAddress.getCity();
        String cityId = city.getIdentity();

        ChronosCityEntityComposite cityPersistentComposite = repository.getInstance( cityId, ChronosCityEntityComposite.class );

        if( cityPersistentComposite != null )
        {
            CountryEntityComposite country = (CountryEntityComposite) validatableAddress.getCountry();
            String countryId = country.getIdentity();

            CountryEntityComposite otherCountry = (CountryEntityComposite) cityPersistentComposite.getCountry();
            String otherCountryId = otherCountry.getIdentity();

            if( countryId.equals( otherCountryId ) )
            {
                StateEntityComposite state = (StateEntityComposite) validatableAddress.getState();
                if( state != null )
                {
                    StateEntityComposite otherState = (StateEntityComposite) cityPersistentComposite.getState();
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
