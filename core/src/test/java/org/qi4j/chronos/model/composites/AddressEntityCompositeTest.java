package org.qi4j.chronos.model.composites;

import org.qi4j.api.CompositeFactory;
import org.qi4j.api.EntityRepository;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.test.model.DummyPersistentStorage;

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
        AddressEntityComposite address = factory.newInstance( AddressEntityComposite.class );

        CityEntityComposite city = factory.newInstance( CityEntityComposite.class );
        city.setIdentity( "Melbourne" );
        city.setName( "Melbourne" );
        address.setCity( city );

        CountryEntityComposite country = factory.newInstance( CountryEntityComposite.class );
        country.setIdentity( "Australia" );
        country.setName( "Australia" );
        address.setCountry( country );

        address.setEntityRepository( new DummyPersistentStorage() );

        // TODO: Test not completed because @Dependency injection in CityStateCountryValidationModifier doesn't work
//        address.validate();
    }

    public void testNewAddressEntityCompositeWithNullIdentity() throws Exception
    {
        AddressEntityComposite addressPC;
        addressPC = factory.newInstance( AddressEntityComposite.class );

        try
        {
            addressPC.setIdentity( null );
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
            CityEntityComposite instance = factory.newInstance( CityEntityComposite.class );
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
