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

import org.junit.After;
import org.junit.Before;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.EntityComposite;

public abstract class AbstractEntityCompositeTest<T extends EntityComposite> extends AbstractCommonTest
{
    protected EntityBuilder<T> compositeBuilder;

    protected Class<T> clazz;

    @Before @Override public void setUp() throws Exception
    {
        super.setUp();

        compositeBuilder = unitOfWork.newEntityBuilder( clazz );
    }

    @After @Override public void tearDown() throws Exception
    {
        compositeBuilder = null;

        super.tearDown();
    }

    @Override public void assemble( ModuleAssembly assembler ) throws AssemblyException
    {
        super.assemble( assembler );

        assembler.addComposites( AccountEntityComposite.class );
    }
}
