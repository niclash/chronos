package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.IPageFactory;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import static org.apache.wicket.PageParameters.NULL;
import org.apache.wicket.WicketRuntimeException;
import static org.qi4j.api.util.NullArgumentException.validateNotNull;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.object.ObjectBuilder;
import org.qi4j.api.object.ObjectBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
final class ChronosPageFactory
    implements IPageFactory
{
    private ObjectBuilderFactory objectBuilderFactory;

    private @Structure UnitOfWorkFactory factory;

    public ChronosPageFactory( @Structure ObjectBuilderFactory anObjectBuilderFactory )
        throws IllegalArgumentException
    {
        validateNotNull( "anObjectBuilderFactory", anObjectBuilderFactory );

        objectBuilderFactory = anObjectBuilderFactory;
    }

    /**
     * Creates a new page using a page class.
     *
     * @param pageClass The page class to instantiate.
     * @return The page.
     * @throws WicketRuntimeException Thrown if the page cannot be constructed
     * @since 0.1.0
     */
    public final <C extends Page> Page newPage( final Class<C> pageClass )
    {
        return newPage( pageClass, NULL );
    }

    /**
     * Creates a new Page, passing PageParameters to the Page constructor if such a constructor
     * exists. If no such constructor exists and the parameters argument is null or empty, then any
     * available default constructor will be used.
     *
     * @param pageClass  The class of Page to create.
     * @param parameters Any parameters to pass to the Page's constructor.
     * @return The new page.
     * @throws WicketRuntimeException Thrown if the page cannot be constructed.
     * @since 0.1.0
     */
    public <C extends Page> Page newPage( final Class<C> pageClass, final PageParameters parameters )
    {
        ObjectBuilder<? extends Page> builder = objectBuilderFactory.newObjectBuilder( pageClass );

        if( parameters != null )
        {
            builder.use( parameters );
        }

        try
        {
            return builder.newInstance();
        }
        catch( Throwable e )
        {
            String msg = "Fail to instantiate Page [" + pageClass.getName() + "] with parameter [" + parameters + "].";
            throw new WicketRuntimeException( msg, e );
        }
    }
}
