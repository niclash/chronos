package org.qi4j.chronos.test;

import static java.lang.Math.min;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.chronos.service.mocks.test.MockPriceRateComposite;
import org.qi4j.chronos.test.util.ValueObjectUtil;
import org.qi4j.chronos.test.util.Comp;
import org.qi4j.chronos.test.util.SomeObjects;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.property.ImmutableProperty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@SuppressWarnings( "hiding" )
public abstract class AbstractCompositeTest<T extends Composite> extends AbstractCommonTest
{

    protected final String PROPERTIES_OF_COMPOSITE = "stateOfComposite";

    protected final String GET = "get";

    protected final String SET = "set";

    protected final String GET_NAME = "name";

    protected Comp<T> comp;
    
    protected ValueObjectUtil<T> vo;

	protected CompositeBuilder<? extends T> builder;

	protected T t;

	@Before
	@Override
	public void setUp() throws Exception
    {
		super.setUp();
	}

	@After
	@Override
	public void tearDown() throws Exception
    {
		super.tearDown();
	}

	public void assemble( ModuleAssembly assembler ) throws AssemblyException
    {
		assembler.addComposites(
            MockPriceRateComposite.class,
            ProjectRoleEntityComposite.class
            );
	}

	protected abstract void init();

	protected void printObject( String...headers )
    {
        if( headers != null )
        {
            for( String header: headers )
            {
                log( header );
            }
        }

        Map<String, SomeObjects> map = comp.getValue();
        for( String method : map.keySet() )
        {
            try
            {
                Object obj = t.getClass().getMethod( method ).invoke( t );
                Object actual = obj.getClass().getMethod( GET ).invoke( obj );
                log( method + ":" + actual.toString() );
            }
            catch( Exception ex )
            {
                err( ex.getLocalizedMessage() );
                ex.printStackTrace();
            }
        }
//		log( vo.toString( t ) );
	}

    protected <K extends Composite> K newInstance( Class<K> clazz, Comp<K> comp )
    {
        CompositeBuilder<K> compositeBuilder = compositeBuilderFactory.newCompositeBuilder( clazz );
        K b = null;
        try
        {
            b = (K) compositeBuilder.getClass().getMethod( PROPERTIES_OF_COMPOSITE ).invoke( compositeBuilder );

        }
        catch( IllegalArgumentException e1 )
        {
            err( e1.getLocalizedMessage() );
            e1.printStackTrace();
        }
        catch( SecurityException e2 )
        {
            err( e2.getLocalizedMessage() );
            e2.printStackTrace();
        }
        catch( IllegalAccessException e3 )
        {
            err( e3.getLocalizedMessage() );
            e3.printStackTrace();
        }
        catch( InvocationTargetException e4 )
        {
            err( e4.getLocalizedMessage() );
            e4.printStackTrace();
        }
        catch( NoSuchMethodException e5 )
        {
            err( e5.getLocalizedMessage() );
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
                    err( e.getLocalizedMessage() );
                    e.printStackTrace();
                }

            }
        }
        return compositeBuilder.newInstance();
    }

    protected <K extends Composite> K newInstance( Class<K> clazz, String[] methods, Object[] values)
    {
		CompositeBuilder<K> aBuilder = compositeBuilderFactory.newCompositeBuilder( clazz );
		K b = null;
		try
		{
			b = (K) aBuilder.getClass().getMethod( PROPERTIES_OF_COMPOSITE ).invoke( aBuilder );
		} catch (IllegalArgumentException e1)
		{
			e1.printStackTrace();
		} catch (SecurityException e1)
		{
			e1.printStackTrace();
		} catch (IllegalAccessException e1)
		{
			e1.printStackTrace();
		} catch (InvocationTargetException e1)
		{
			e1.printStackTrace();
		} catch (NoSuchMethodException e1)
		{
			e1.printStackTrace();
		}

		if( b != null)
		{
			for (int i = 0; i < min(methods.length, values.length); i++)
			{
				try
				{
					Object obj = clazz.getMethod(methods[i]).invoke(b);
					Class cl = obj.getClass().getMethod("get").getReturnType();
					obj.getClass().getMethod("set", cl).setAccessible(true);
					obj.getClass().getMethod("set", cl).invoke(obj, values[i]);
				} catch (Exception e)
				{
					err( e.getLocalizedMessage() );
					e.printStackTrace();
				}
			}
		}
		return aBuilder.newInstance();
	}

    protected void update()
    {
        Map<String, SomeObjects> map = comp.getValue();
        for( String method : map.keySet() )
        {
            try
            {
                Object obj = t.getClass().getMethod( method ).invoke( t );
                Class cl = obj.getClass().getMethod( GET ).getReturnType();
                obj.getClass().getMethod( SET, cl ).setAccessible( true );
                try
                {
                    log( "Updating " + method );
                    obj.getClass().getMethod( SET, cl ).invoke( obj, map.get( method ).getUpdated() );
                }
                catch( Exception ex )
                {
                    err( ex.getLocalizedMessage() );
                }
            }
            catch( Exception ex )
            {
                err( ex.getLocalizedMessage() );
                ex.printStackTrace();
            }
        }
    }

    protected void validate( boolean isChanged )
    {
        Map<String, SomeObjects> map = comp.getValue();
        for( String method : map.keySet() )
        {
            try
            {
                Object obj = t.getClass().getMethod( method ).invoke( t );
                Object actual = obj.getClass().getMethod( GET ).invoke( obj );
                Object expected = isChanged ? map.get( method ).getUpdated() : map.get( method ).getValue();
                String mesg = "MutableProperty:" + method + " has not changed!!!";

                if( obj instanceof ImmutableProperty )
                {
                    mesg = "ImmutableProperty:" + method + " has changed!!!";
                    if( isChanged )
                    {
                        assertNotSame( mesg, expected, actual );
                    }
                    expected = map.get( method ).getValue();
                }
                assertEquals( mesg, expected, actual );
            }
            catch( Exception ex )
            {
                err( ex.getLocalizedMessage() );
                ex.printStackTrace();
            }
        }
    }
}
