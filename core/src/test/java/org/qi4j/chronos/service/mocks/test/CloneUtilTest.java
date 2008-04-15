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
package org.qi4j.chronos.service.mocks.test;

import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.service.mocks.CloneUtil;
import org.qi4j.chronos.test.util.Comp;
import org.qi4j.chronos.test.util.SomeObjects;
import org.qi4j.chronos.test.util.CompImpl;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.property.ImmutableProperty;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.min;

public class CloneUtilTest
{
    public static final String PROPERTIES_OF_COMPOSITE = "stateOfComposite";

    public static final String GET = "get";

    public static final String SET = "set";
    
//    @Test
    public void cloneLegalConditionTest()
    {
        System.out.println( "CloneUtilTest.cloneLegalConditionTest" );
        SingletonAssembler assembler = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.addComposites( MockLegalConditionComposite.class );
            }
        };

        MockLegalConditionComposite mockLegalConditionComposite = assembler.getCompositeBuilderFactory().newComposite( MockLegalConditionComposite.class );
        mockLegalConditionComposite.name().set( "test" );
        mockLegalConditionComposite.description().set( "test" );

        //LegalConditionEntityComposite cloned = CloneUtil.cloneLegalCondition( assembler.getCompositeBuilderFactory(), mockLegalConditionComposite );
        //assertEquals( "Cloned objects are not equal!!!", mockLegalConditionComposite, cloned );

        System.out.println( mockLegalConditionComposite.toString() );
    }

    @Test
    public void priceRateTest()
    {
        System.out.println( CloneUtilTest.class.getSimpleName() + ".priceRateTest" );
        SingletonAssembler assembler = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.addComposites( MockPriceRateComposite.class );
                module.addComposites( MockProjectRoleComposite.class );
            }
        };

//        ProjectRoleComposite projectRoleComposite = assembler.getCompositeBuilderFactory().newComposite( ProjectRoleComposite.class );
//        projectRoleComposite.name().set( "test" );

//        ProjectRoleComposite projectRoleComposite = newInstance( assembler.getCompositeBuilderFactory(), MockProjectRoleComposite.class, new String[]{ "name" }, new Object[]{ "test" } );
//
//        PriceRateComposite priceRateComposite = assembler.getCompositeBuilderFactory().newComposite( PriceRateComposite.class );
//        priceRateComposite.priceRateType().set( PriceRateType.HOURLY );
//        priceRateComposite.projectRole().set( projectRoleComposite );
//
//        PriceRateComposite cloned = CloneUtil.clonePriceRate( assembler.getCompositeBuilderFactory(), priceRateComposite );
//        assertEquals( "Cloned objects are not equals!!!", priceRateComposite, cloned );
//
//        System.out.println( priceRateComposite.toString() );

        ProjectRoleComposite composite = newInstance( assembler.getCompositeBuilderFactory(), MockProjectRoleComposite.class, new CompImpl( assembler.getCompositeBuilderFactory().newCompositeBuilder( ProjectRoleComposite.class )) );
        System.out.println( composite.name() );
        System.out.println( composite.toString() );
    }

//    @Test
    public void updateTest()
    {
        System.out.println( CloneUtilTest.class.getSimpleName() + ".updateTest" );
        SingletonAssembler assembler = new SingletonAssembler()
        {
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.addComposites( MockProjectRoleComposite.class );
            }
        };
        CompositeBuilderFactory compositeBuilderFactory = assembler.getCompositeBuilderFactory();
        CompositeBuilder<ProjectRoleComposite> compositeBuilder = compositeBuilderFactory.newCompositeBuilder( ProjectRoleComposite.class );
        Comp comp = new CompImpl( compositeBuilder ); 
        ProjectRoleComposite composite = newInstance( compositeBuilderFactory, MockProjectRoleComposite.class, comp );
        System.out.println( "Before---" );
        System.out.println( "composite.identity().get() = " + composite.identity().get() );
        System.out.println( "composite.name().get() = " + composite.name().get() );
        compositeUpdate( composite, comp );
        System.out.println( "After---" );
        System.out.println( "composite.identity().get() = " + composite.identity().get() );
        System.out.println( "composite.name().get() = " + composite.name().get() );
    }

    public void compositeUpdate( ProjectRoleComposite p, Comp comp )
    {
        Map<String, SomeObjects> map = comp.getValue();
        for( String method : map.keySet() )
        {
            try
            {
                Object obj = p.getClass().getMethod( method ).invoke( p );
                Class cl = obj.getClass().getMethod( GET ).getReturnType();
                obj.getClass().getMethod( SET, cl ).setAccessible( true );
                try
                {
                    obj.getClass().getMethod( SET, cl ).invoke( obj, map.get( method ).getUpdated() );
                }
                catch( Exception e )
                {
                    // ignore
                }

                if( obj instanceof ImmutableProperty )
                {
                    assertEquals( "ImmutableProperty has changed!!!!", map.get( method ).getValue(), obj.getClass().getMethod( GET ).invoke( obj ) );
                }
                else
                {
                    assertEquals( "MutableProperty has not changed!!!!", map.get( method ).getUpdated(), obj.getClass().getMethod( GET ).invoke( obj ) );
                }
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    public <K extends Composite> K newInstance( CompositeBuilderFactory factory, Class<K> clazz, String[] methods, Object[] values )
    {
        CompositeBuilder<K> aBuilder = factory.newCompositeBuilder( clazz );
        K b = null;
        try
        {
            b = (K) aBuilder.getClass().getMethod( PROPERTIES_OF_COMPOSITE ).invoke( aBuilder );
        }
        catch( IllegalArgumentException e1 )
        {
            e1.printStackTrace();
        }
        catch( SecurityException e1 )
        {
            e1.printStackTrace();
        }
        catch( IllegalAccessException e1 )
        {
            e1.printStackTrace();
        }
        catch( InvocationTargetException e1 )
        {
            e1.printStackTrace();
        }
        catch( NoSuchMethodException e1 )
        {
            e1.printStackTrace();
        }

        if( b != null )
        {
            for( int i = 0; i < min( methods.length, values.length ); i++ )
            {
                try
                {
                    Object obj = clazz.getMethod( methods[ i ] ).invoke( b );
                    Class cl = obj.getClass().getMethod( GET ).getReturnType();
                    obj.getClass().getMethod( SET, cl ).setAccessible( true );
                    obj.getClass().getMethod( SET, cl ).invoke( obj, values[ i ] );
                }
                catch( Exception e )
                {
                    System.err.println( e.getLocalizedMessage() );
                    e.printStackTrace();
                }
            }
        }
        return aBuilder.newInstance();
    }

    public <K extends Composite> K newInstance( CompositeBuilderFactory compositeBuilderFactory, Class<K> clazz, Comp comp )
    {
        CompositeBuilder<K> compositeBuilder = compositeBuilderFactory.newCompositeBuilder( clazz );
        K b = null;
        try
        {
            b = (K) compositeBuilder.getClass().getMethod( PROPERTIES_OF_COMPOSITE ).invoke( compositeBuilder );

        }
        catch( IllegalArgumentException e1 )
        {
            e1.printStackTrace();
        }
        catch( SecurityException e2 )
        {
            e2.printStackTrace();
        }
        catch( IllegalAccessException e3 )
        {
            e3.printStackTrace();
        }
        catch( InvocationTargetException e4 )
        {
            e4.printStackTrace();
        }
        catch( NoSuchMethodException e5 )
        {
            e5.printStackTrace();
        }

        if( b!= null )
        {
            Map<String, SomeObjects> map = comp.getValue();
            for( String method : map.keySet() )
            {
                try
                {
                    Object obj = clazz.getMethod( method ).invoke( b );
                    Class cl = obj.getClass().getMethod( GET ).getReturnType();
                    obj.getClass().getMethod( SET, cl ).setAccessible( true );
                    obj.getClass().getMethod( SET, cl ).invoke( obj, map.get( method ).getValue() );
                }
                catch( Exception e)
                {
                    System.err.println( e.getLocalizedMessage() );
                    e.printStackTrace();
                }

            }
        }
        return compositeBuilder.newInstance();
    }

}
