/*
 * Copyright (c) 2008, kamil. All Rights Reserved.
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

import org.junit.Before;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.Identity;

public class ProductEntityCompositeTest extends AbstractCommonTest
{
    @Override
    public void assemble( ModuleAssembly assembler ) throws AssemblyException
    {
        super.assemble( assembler );
        assembler.addEntities( ProductEntityComposite.class, ItemComposite.class );
    }

    @Test public void testA() throws Exception
    {
        EntityBuilder<ItemComposite> builder = unitOfWork.newEntityBuilder( ItemComposite.class );
        ItemComposite item = builder.newInstance();
        item.name().set( "test" );

        EntityBuilder<ProductEntityComposite> build = unitOfWork.newEntityBuilder( ProductEntityComposite.class );
        ProductEntityComposite product = build.newInstance();
        product.item().set( item );
        String ID = ( (Identity) product ).identity().get();
        unitOfWork.complete();

        unitOfWork = unitOfWorkFactory.newUnitOfWork();

        ProductEntityComposite p = unitOfWork.find( ID, ProductEntityComposite.class );

        System.out.println( p.item().get().name().get() );
    }
}
