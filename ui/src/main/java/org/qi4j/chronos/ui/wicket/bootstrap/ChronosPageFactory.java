package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.IPageFactory;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.UnitOfWorkFactory;

/**
 * @author edward.yakop@gmail.com
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

    @SuppressWarnings( "unchecked" )
    public final Page newPage( Class pageClass )
    {
        ObjectBuilder<Page> builder = objectBuilderFactory.newObjectBuilder( pageClass );

        return builder.newInstance();
    }

    public final Page newPage( Class pageClass, PageParameters parameters )
    {
        // TODO: EFY: We don't have a way to pass page parameters yet.
        ObjectBuilder<Page> builder = objectBuilderFactory.newObjectBuilder( pageClass );
        
        if( null != parameters )
        {
                builder.use( parameters );
        }

        return builder.newInstance();
    }
}
