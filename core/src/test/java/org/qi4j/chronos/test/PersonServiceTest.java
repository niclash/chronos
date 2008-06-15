/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.object.ObjectBuilder;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;

public class PersonServiceTest extends AbstractCommonTest
{
    @Override public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addObjects( Items.class );
        module.addEntities( PersonEntity.class, PersonServiceConfiguration.class );
        module.addComposites( ItemCompositeToo.class );
        module.addServices( PersonServiceComposite.class, UuidIdentityGeneratorService.class, MemoryEntityStoreService.class );
    }

    @Test
    public void personTest()
        throws Exception
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();
        try
        {
            PersonServiceComposite personService = serviceLocator.findService( PersonServiceComposite.class ).get();

            ObjectBuilder<Items> objectBuilder = uow.objectBuilderFactory().newObjectBuilder( Items.class );
            Items item = objectBuilder.newInstance();
            item.set( "test" );

            PersonEntity p = personService.newInstance( uow );
            p.name().set( "test" );
            p.item().set( item );
            personService.save( p );

            PersonEntity p2 = personService.newInstance( uow );
            p2.name().set( "another test" );
            p2.item().set( item );
            personService.save( p2 );

            String id = p2.identity().get();
            uow.complete();

            assertEquals( "Size not equals to 2", 2, personService.count() );

            for( PersonEntity person : personService.findAll() )
            {
                System.out.println( person.name().get() );
                System.out.println( person.item().get() );
            }

            uow = unitOfWorkFactory.newUnitOfWork();

            PersonEntity deletePerson = uow.dereference( p2 );
            personService.delete( uow, deletePerson );
            uow.complete();

            assertEquals( "Size not equals to 1", 1, personService.count() );

            for( PersonEntity person : personService.findAll() )
            {
                System.out.println( person.name().get() );
            }

            uow = unitOfWorkFactory.newUnitOfWork();

            try
            {
                PersonEntity findPerson = uow.find( id, PersonEntity.class );
                System.out.println( findPerson.name().get() );
                fail( "Should not be able to find entity" );
            }
            catch( EntityCompositeNotFoundException ecnfe )
            {
                assertEquals( "This is expected", 1, 1 );
            }
        }
        catch( RuntimeException e )
        {
            uow.discard();
            throw e;
        }
    }
}
