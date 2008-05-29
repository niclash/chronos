/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.wicket.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;
import org.qi4j.test.AbstractQi4jTest;

/**
 * @author Lan Boon Ping
 */
public class ChronosPropertyResolverTest extends AbstractQi4jTest
{
    private UnitOfWork unitOfWork;

    @Before
    public void setUp() throws Exception
    {
        super.setUp();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();
    }

    @After
    public void tearDown() throws Exception
    {
        try
        {
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException err )
        {
            //ignore
        }
        finally
        {
            unitOfWork = null;
        }

        super.tearDown();
    }

    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addComposites( CustomerEntityComposite.class );
        module.addComposites( AddressEntityComposite.class );
        module.addComposites( CityEntityComposite.class );
        module.addComposites( StateEntityComposite.class );
        module.addComposites( CountryEntityComposite.class );

        module.addServices( UuidIdentityGeneratorService.class, MemoryEntityStoreService.class );
    }

    @Test
    public void testEntitySimpleProperty()
    {
        CustomerEntityComposite customer = unitOfWork.newEntity( CustomerEntityComposite.class );
        customer.name().set( "Microsoft" );

        Assert.assertEquals( "Microsoft", ChronosPropertyResolver.getValue( "name", customer ) );

        ChronosPropertyResolver.setValue( "name", customer, "Yahoo" );

        Assert.assertEquals( "Yahoo", customer.name().get() );
    }

    @Test
    public void testEntityChainedProperty()
    {
        CityEntityComposite city = unitOfWork.newEntity( CityEntityComposite.class );
        city.name().set( "KL" );

        CountryEntityComposite country = unitOfWork.newEntity( CountryEntityComposite.class );
        country.name().set( "Malaysia" );

        StateEntityComposite state = unitOfWork.newEntity( StateEntityComposite.class );
        state.name().set( "Wilayah" );

        city.state().set( state );
        city.country().set( country );

        AddressEntityComposite address = unitOfWork.newEntity( AddressEntityComposite.class );
        address.firstLine().set( "B-7-4 Megan Avenue" );
        address.city().set( city );

        CustomerEntityComposite customer = unitOfWork.newEntity( CustomerEntityComposite.class );
        customer.name().set( "Microsoft" );
        customer.address().set( address );

        //test getvalue
        Assert.assertEquals( "Microsoft", ChronosPropertyResolver.getValue( "name", customer ) );

        Assert.assertEquals( "B-7-4 Megan Avenue", ChronosPropertyResolver.getValue( "address.firstLine", customer ) );

        Assert.assertEquals( "KL", ChronosPropertyResolver.getValue( "address.city.name", customer ) );

        Assert.assertEquals( "Wilayah", ChronosPropertyResolver.getValue( "address.city.state.name", customer ) );
        Assert.assertEquals( "Malaysia", ChronosPropertyResolver.getValue( "address.city.country.name", customer ) );

        //test setvalue
        ChronosPropertyResolver.setValue( "name", customer, "Yahoo" );
        ChronosPropertyResolver.setValue( "address.firstLine", customer, "Gold Road 33" );
        ChronosPropertyResolver.setValue( "address.city.name", customer, "PJ" );
        ChronosPropertyResolver.setValue( "address.city.state.name", customer, "Selangor" );
        ChronosPropertyResolver.setValue( "address.city.country.name", customer, "Thailand" );

        Assert.assertEquals( "Yahoo", customer.name().get() );
        Assert.assertEquals( "Gold Road 33", customer.address().get().firstLine().get() );
        Assert.assertEquals( "PJ", customer.address().get().city().get().name().get() );
        Assert.assertEquals( "Selangor", customer.address().get().city().get().state().get().name().get() );
        Assert.assertEquals( "Thailand", customer.address().get().city().get().country().get().name().get() );
    }

    @Test
    public void testInvalidPropertyName()
    {
        CustomerEntityComposite customer = unitOfWork.newEntity( CustomerEntityComposite.class );

        try
        {
            ChronosPropertyResolver.getValue( "nonExistingProperty", customer );
            Assert.fail( "Exception should be thrown here." );
        }
        catch( Exception err )
        {
            Assert.assertEquals( "Cannot find method corresponding to nonExistingProperty", err.getMessage() );
        }

        try
        {
            ChronosPropertyResolver.setValue( "nonExistingProperty", customer, new Object() );

            Assert.fail( "Exception should be thrown here." );
        }
        catch( Exception err )
        {
            Assert.assertEquals( "Cannot find method corresponding to nonExistingProperty", err.getMessage() );
        }
    }
}
