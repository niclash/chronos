/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 12, 2008
 * Time: 2:51:41 AM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.test;

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.Account;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.spi.entity.EntityNotFoundException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountEntityCompositeTest extends AbstractEntityCompositeTest<AccountEntityComposite>
{
    @Before @Override public void setUp() throws Exception
    {
        this.clazz = AccountEntityComposite.class;

        super.setUp();
    }

    @Test public void createTest() throws Exception
    {
        Account account = compositeBuilder.newInstance();
        account.name().set( "test" );
        account.reference().set( "test" );
        account.isEnabled().set( true );
        String id = ( (AccountEntityComposite) account).identity().get();
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();
        Account byRef = unitOfWork.dereference( account );

        assertEquals( "Name not equals to test!!!", "test", byRef.name().get() );

        Account byId = unitOfWork.find( id, clazz );

        assertEquals( "Name not equals to test!!!", "test", byId.name().get() );
    }

    @Test public void deleteTest() throws Exception
    {
        Account account = compositeBuilder.newInstance();
        account.name().set( "test" );
        account.reference().set( "test" );
        account.isEnabled().set( true );
        String id = ( (AccountEntityComposite) account).identity().get();
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        account = unitOfWork.dereference( account );

        unitOfWork.remove( account );
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        try
        {
            Account byId = unitOfWork.find( id, clazz );
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