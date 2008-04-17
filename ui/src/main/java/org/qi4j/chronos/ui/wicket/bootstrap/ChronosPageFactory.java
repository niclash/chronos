package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.IPageFactory;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.systemrole.SystemRoleService;

/**
 * @author edward.yakop@gmail.com
 */
final class ChronosPageFactory
    implements IPageFactory
{
    private ObjectBuilderFactory objectBuilderFactory;

    private @Structure UnitOfWorkFactory factory;

    private @Service AccountService accountService;

    private @Service SystemRoleService systemRoleService;

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
        builder.use( factory, accountService, systemRoleService );

        return builder.newInstance();
    }

    public final Page newPage( Class pageClass, PageParameters parameters )
    {
        // TODO: EFY: We don't have a way to pass page parameters yet.
//        return newPage( pageClass );
        ObjectBuilder<Page> builder = objectBuilderFactory.newObjectBuilder( pageClass );
        builder.use( factory, accountService );
        if( null!= parameters )
        {
            for( Object obj : parameters.values() )
            {
                builder.use( obj );
            }
        }

        return builder.newInstance();
    }
}
