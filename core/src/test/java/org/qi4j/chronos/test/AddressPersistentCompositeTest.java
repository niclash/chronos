package org.qi4j.chronos.test;

import org.qi4j.api.persistence.PersistentStorage;
import org.qi4j.chronos.model.composites.AddressPersistentComposite;

public class AddressPersistentCompositeTest extends AbstractTest
{
    private PersistentStorage storage;

    protected void setUp() throws Exception
    {
        super.setUp();

//        Mockery context = new JUnit4Mockery();
//        storage = context.mock( PersistentStorage.class );
    }

    public void testNewAddressEntity() throws Exception
    {
/*
        AddressPersistentComposite address = factory.newInstance( AddressPersistentComposite.class );
        address.setPersistentStorage( storage );

        address.setCityName( "Melbourne" );
        address.setCountryName( "Australia" );

        address.validate();
*/
    }

    public void testNewAddressPersistentCompositeWithNullIdentity() throws Exception
    {
        AddressPersistentComposite addressPC;
        addressPC = factory.newInstance( AddressPersistentComposite.class );

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
}
