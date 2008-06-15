/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 12, 2008
 * Time: 2:51:41 AM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.spi.entity.EntityNotFoundException;

public class AccountEntityCompositeTest extends AbstractCommonTest
{
    @Test public void createTest() throws Exception
    {
        Account account = unitOfWork.newEntity( Account.class );
        account.name().set( "test" );
        account.reference().set( "test" );
        account.isEnabled().set( true );
        String id = ( (AccountEntityComposite) account ).identity().get();
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();
        Account byRef = unitOfWork.dereference( account );

        assertEquals( "Name not equals to test!!!", "test", byRef.name().get() );

        Account byId = unitOfWork.find( id, Account.class );

        assertEquals( "Name not equals to test!!!", "test", byId.name().get() );
    }

    @Test public void deleteTest() throws Exception
    {
        Account account = unitOfWork.newEntity( Account.class );
        account.name().set( "test" );
        account.reference().set( "test" );
        account.isEnabled().set( true );
        String id = ( (AccountEntityComposite) account ).identity().get();
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        account = unitOfWork.dereference( account );

        unitOfWork.remove( account );
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        try
        {
            Account byId = unitOfWork.find( id, Account.class );
            fail( "This should not happen!!! Entity has been deleted.." );
        }
        catch( EntityCompositeNotFoundException enfe )
        {
            // expected
        }

        try
        {
            Account byRef = unitOfWork.dereference( account );
            System.out.println( byRef.name().get() );
            fail( "This should not happen!!! Entity has been deleted.." );
        }
        catch( EntityNotFoundException enfe )
        {
            // expected
        }
    }
}
