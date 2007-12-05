package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.IPageFactory;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.qi4j.annotation.scope.Structure;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 */
final class ChronosPageFactory
    implements IPageFactory
{
    private ObjectBuilderFactory objectBuilderFactory;

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
        return newPage( pageClass );
    }
}
