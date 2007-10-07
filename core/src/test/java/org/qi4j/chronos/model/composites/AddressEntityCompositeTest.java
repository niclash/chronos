package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;

public class AddressEntityCompositeTest extends AbstractTest
{
    public void testNewAddressCompositeSuccessful() throws Exception
    {
        AddressComposite address = builderFactory.newCompositeBuilder( AddressComposite.class ).newInstance();

        String firstLineAdd = "IOI Tower";
        String secondLineAdd = "101 Collins St.";
        String thirdLineAdd = null;
        String zipcode = "3000";
        String cityName = "Melbourne";
        String stateName = "Victoria";
        String countryName = "Australia";

        address.setFirstLine( firstLineAdd );
        address.setSecondLine( secondLineAdd );
        address.setZipCode( zipcode );

        CityComposite city = builderFactory.newCompositeBuilder( CityComposite.class ).newInstance();
        city.setName( cityName );
        address.setCity( city );

        StateComposite state = builderFactory.newCompositeBuilder( StateComposite.class ).newInstance();
        state.setName( "Victoria" );
        city.setState( state );

        CountryComposite country = builderFactory.newCompositeBuilder( CountryComposite.class ).newInstance();
        country.setName( "Australia" );
        city.setCountry( country );

        assertEquals( firstLineAdd, address.getFirstLine() );
        assertEquals( secondLineAdd, address.getSecondLine() );
        assertEquals( zipcode, address.getZipCode() );

        CityComposite otherCity = (CityComposite) address.getCity();

        assertEquals( city.getCompositeModel(), otherCity.getCompositeModel() );
        assertEquals( cityName, otherCity.getName() );

        StateComposite otherState = (StateComposite) city.getState();
        assertEquals( state.getCompositeModel(), otherState.getCompositeModel() );
        assertEquals( stateName, otherState.getName() );

        CountryComposite otherCountry = (CountryComposite) city.getCountry();
        assertEquals( country.getCompositeModel(), otherCountry.getCompositeModel() );
        assertEquals( countryName, otherCountry.getName() );
    }

    public void testValidateAddressEntityComposite() throws Exception
    {
        AddressComposite address = builderFactory.newCompositeBuilder( AddressComposite.class ).newInstance();

        address.setFirstLine( "502 King St." );
        address.setZipCode( "3000" );

        CityComposite city = builderFactory.newCompositeBuilder( CityComposite.class ).newInstance();
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryComposite country = builderFactory.newCompositeBuilder( CountryComposite.class ).newInstance();
        country.setName( "Australia" );
        city.setCountry( country );
        address.checkValid();
    }

    public void testValidateCityNull() throws Exception
    {
        AddressComposite address = builderFactory.newCompositeBuilder( AddressComposite.class ).newInstance();
        address.setFirstLine( "502 King St." );
        address.setZipCode( "3000" );

        try
        {
            address.checkValid();
            fail( "ValidationException should be thrown as City is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateFirstLineNull() throws Exception
    {
        AddressComposite address = builderFactory.newCompositeBuilder( AddressComposite.class ).newInstance();
        address.setZipCode( "3000" );

        CityComposite city = builderFactory.newCompositeBuilder( CityComposite.class ).newInstance();
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryComposite country = builderFactory.newCompositeBuilder( CountryComposite.class ).newInstance();
        country.setName( "Australia" );
        city.setCountry( country );

        try
        {
            address.checkValid();
            fail( "ValidationException should be thrown as first line address is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateZipCodeNull() throws Exception
    {
        AddressComposite address = builderFactory.newCompositeBuilder( AddressComposite.class ).newInstance();
        address.setFirstLine( "502 King St." );

        CityComposite city = builderFactory.newCompositeBuilder( CityComposite.class ).newInstance();
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryComposite country = builderFactory.newCompositeBuilder( CountryComposite.class ).newInstance();
        country.setName( "Australia" );
        city.setCountry( country );

        try
        {
            address.checkValid();
            fail( "ValidationException should be thrown as zipcode is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}
