package org.qi4j.chronos.ui.wicket;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblerException;
import org.qi4j.bootstrap.LayerAssembly;
import static org.qi4j.chronos.ui.wicket.Constants.LAYER_NAME_WICKET;
import static org.qi4j.chronos.ui.wicket.authentication.WicketAuthenticationModuleInitializer.addWicketAuthenticationModule;
import static org.qi4j.chronos.ui.wicket.bootstrap.WicketBootstrapModuleInitializer.addWicketBootstrapModule;
import static org.qi4j.composite.NullArgumentException.validateNotNull;

/**
 * {@code WicketAuthenticationModuleInitializer} add wicket layer assembly to an application assembly instance.
 *
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class WicketLayerAssemblyInitializer
{
    private WicketLayerAssemblyInitializer()
    {
    }

    /**
     * Add wicket layer assembly.
     *
     * @param anApplicationAssembly The application assembly. This argument must not be {@code null}.
     * @throws IllegalArgumentException Thrown if the specified {@code anApplicationAssembly} argument is {@code null}.
     * @since 0.1.0
     */
    public static void addWicketLayerAssembly( ApplicationAssembly anApplicationAssembly )
        throws IllegalArgumentException, AssemblerException

    {
        validateNotNull( "anApplicationAssembly", anApplicationAssembly );

        LayerAssembly wicketLayerAssembly = anApplicationAssembly.newLayerBuilder();
        wicketLayerAssembly.setName( LAYER_NAME_WICKET );
        addWicketBootstrapModule( wicketLayerAssembly );
        addWicketAuthenticationModule( wicketLayerAssembly );
    }
}
