package org.qi4j.chronos.model.composites;

import org.qi4j.api.CompositeFactory;
import org.qi4j.api.EntityRepository;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.composites.CountryEntityComposite;

public class AddressEntityCompositeTest extends AbstractTest
{
    private EntityRepository repository;

    protected void setUp() throws Exception
    {
        super.setUp();
        repository = new AddressEntityRepository( factory );
    }

    public void testNewAddressEntityComposite() throws Exception
    {
        ValidatableAddressEntityComposite validatableAddress = factory.newInstance( ValidatableAddressEntityComposite.class );

        ChronosCityEntityComposite city = factory.newInstance( ChronosCityEntityComposite.class );
        city.setIdentity( "Melbourne" );
        city.setName( "Melbourne" );
        validatableAddress.setCity( city );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIdentity( "Australia" );
        country.setName( "Australia" );
        validatableAddress.setCountry( country );

//        validatableAddress.setEntityRepository( new DummyPersistentStorage() );

        // TODO: Test not completed because @Dependency injection in CityStateCountryValidationModifier doesn't work
//        validatableAddress.validate();
    }

    public void testNewAddressEntityCompositeWithNullIdentity() throws Exception
    {
        ValidatableAddressEntityComposite validatableAddressPC;
        validatableAddressPC = factory.newInstance( ValidatableAddressEntityComposite.class );

        try
        {
            validatableAddressPC.setIdentity( null );
            fail( "Identity should not be null." );
        }
        catch( NullPointerException e )
        {
            // Correct
        }
    }

    public class AddressEntityRepository implements EntityRepository
    {
        private CompositeFactory factory;

        public AddressEntityRepository( CompositeFactory aFactory )
        {
            factory = aFactory;
        }

        public <T extends EntityComposite> T getInstance( String identity, Class<T> type )
        {
            ChronosCityEntityComposite instance = factory.newInstance( ChronosCityEntityComposite.class );
            instance.setIdentity( identity );
            instance.setName( "Melbourne" );

            CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
            country.setIdentity( "Australia" );
            country.setName( "Australia" );
            instance.setCountry( country );

            return (T) instance;
        }

        public <T extends EntityComposite> T getInstance( String identity, Class<T> type, boolean autoCreate )
        {
            return factory.newInstance( type );
        }

        public <T extends EntityComposite> T newInstance( String identity, Class<T> type )
        {
            return factory.newInstance( type );
        }
    }
}
