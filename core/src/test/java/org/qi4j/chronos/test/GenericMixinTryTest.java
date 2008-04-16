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

import org.qi4j.test.AbstractQi4jTest;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.chronos.model.GenericMixinTry;
import org.qi4j.property.Property;
import org.qi4j.entity.association.AbstractAssociation;
import org.qi4j.entity.association.SetAssociation;
import org.junit.Test;

public class GenericMixinTryTest extends AbstractQi4jTest
{
    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addComposites( GenericMixinTryTest.NameComposite.class );
    }

    @Test public void createTest() throws Exception
    {
        Name name = compositeBuilderFactory.newCompositeBuilder( NameComposite.class ).newInstance();
        name.setName( "test" );

        System.out.println( "Name is " + name.name().get() );
        System.out.println( "Name is " + name.getName() );

        if( AbstractAssociation.class.isAssignableFrom( SetAssociation.class ) )
        {
            System.out.println( "AbstractAssociation is assignable from SetAssociation." );
        }
    }

    @Mixins( GenericMixinTry.class )
    public interface Name
    {
        Property<String> name();

        String getName();

        void setName( String name );
    }

    private static interface NameComposite extends Composite, Name
    {
    }
}
