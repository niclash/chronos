package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.composites.CityEntityComposite;
import org.qi4j.library.general.model.composites.CountryEntityComposite;
import org.qi4j.library.general.model.composites.StateEntityComposite;

public class AddressEntityCompositeTest extends AbstractTest
{
    public void testNewAddressCompositeSuccessful() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );

        String firstLineAdd = "IOI Tower";
        String secondLineAdd = "101 Collins St.";
        String thirdLineAdd = null;
        String zipcode = "3000";
        String cityName = "Melbourne";
        String stateName = "Victoria";
        String countryName = "Australia";

        address.setFirstLine( firstLineAdd );
        address.setSecondLine( secondLineAdd );
        address.setThirdLine( thirdLineAdd );
        address.setZipCode( zipcode );

        CityEntityComposite city = factory.newInstance( CityEntityComposite.class );
        city.setName( cityName );
        address.setCity( city );

        StateEntityComposite state = factory.newInstance( StateEntityComposite.class );
        state.setName( "Victoria" );
        city.setState( state );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIsoCode( "AU" );
        country.setName( "Australia" );
        city.setCountry( country );

        assertEquals( firstLineAdd, address.getFirstLine() );
        assertEquals( secondLineAdd, address.getSecondLine() );
        assertNull( address.getThirdLine() );
        assertEquals( zipcode, address.getZipCode() );

        CityEntityComposite otherCity = (CityEntityComposite) address.getCity();

        assertEquals( city.getCompositeModel(), otherCity.getCompositeModel() );
        assertEquals( cityName, otherCity.getName() );

        StateEntityComposite otherState = (StateEntityComposite) city.getState();
        assertEquals( state.getCompositeModel(), otherState.getCompositeModel() );
        assertEquals( stateName, otherState.getName() );

        CountryEntityComposite otherCountry = (CountryEntityComposite) city.getCountry();
        assertEquals( country.getCompositeModel(), otherCountry.getCompositeModel() );
        assertEquals( countryName, otherCountry.getName() );
    }

    public void testValidateAddressEntityComposite() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );

        address.setFirstLine( "502 King St." );
        address.setZipCode( "3000" );

        CityEntityComposite city = factory.newInstance( CityEntityComposite.class );
        city.setIdentity( "Melbourne" );
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIdentity( "Australia" );
        country.setName( "Australia" );
        city.setCountry( country );
        address.validate();
    }

    public void testValidateCityNull() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );
        address.setFirstLine( "502 King St." );
        address.setZipCode( "3000" );

        try
        {
            address.validate();
            fail( "ValidationException should be thrown as City is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateFirstLineNull() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );
        address.setZipCode( "3000" );

        CityEntityComposite city = factory.newInstance( CityEntityComposite.class );
        city.setIdentity( "Melbourne" );
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIdentity( "Australia" );
        country.setName( "Australia" );
        city.setCountry( country );

        try
        {
            address.validate();
            fail( "ValidationException should be thrown as first line address is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateZipCodeNull() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );
        address.setFirstLine( "502 King St." );

        CityEntityComposite city = factory.newInstance( CityEntityComposite.class );
        city.setIdentity( "Melbourne" );
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIdentity( "Australia" );
        country.setName( "Australia" );
        city.setCountry( country );

        try
        {
            address.validate();
            fail( "ValidationException should be thrown as zipcode is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testNewAddressEntityCompositeWithNullIdentity() throws Exception
    {
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );

        try
        {
            address.setIdentity( null );
            fail( "Identity should not be null." );
        }
        catch( NullPointerException e )
        {
            // Correct
        }
    }

}
