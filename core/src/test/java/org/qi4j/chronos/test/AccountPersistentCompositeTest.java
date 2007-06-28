package org.qi4j.chronos.test;

import org.qi4j.chronos.model.composites.AccountPersistentComposite;
import org.qi4j.library.general.model.ValidationException;

public class AccountPersistentCompositeTest extends AbstractTest
{
    public void testAccountNameNotNull()
    {
        AccountPersistentComposite account = factory.newInstance( AccountPersistentComposite.class );

        try
        {
            account.setName( null );
            fail( "Should throw ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }
    }

    public void testAccountContent()
    {
        //TODO
    }
}
