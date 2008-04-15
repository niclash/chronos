package org.qi4j.chronos.ui.wicket.authentication;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import static org.qi4j.chronos.ui.wicket.authentication.Constants.*;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.authentication.AuthenticationConfiguration;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.structure.Visibility;
import org.qi4j.entity.memory.MemoryEntityStoreService;

/**
 * {@code WicketAuthenticationModuleInitializer} initialize wicket authentication module.
 *
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class WicketAuthenticationModuleInitializer
{
    private WicketAuthenticationModuleInitializer()
    {
    }

    /**
     * Add The specified wicket authentication module to the specified {@code aLayerAssembly}.
     *
     * @param aLayerAssembly The layer assembly. This argument must not be {@code null}.
     * @throws IllegalArgumentException Thrown if the specified {@code aLayerAssembly} is {@code null}.
     * @since 0.1.0
     */
    public static void addWicketAuthenticationModule( LayerAssembly aLayerAssembly )
        throws IllegalArgumentException, AssemblyException

    {
        validateNotNull( "aModuleAssembly", aLayerAssembly );

        ModuleAssembly moduleAssembly = aLayerAssembly.newModuleAssembly();
        moduleAssembly.setName( MODULE_NAME_WICKET_AUTHENTICATION );
        moduleAssembly.addComposites( AuthenticationConfiguration.class, RoleHelperComposite.class ).visibleIn( Visibility.layer );
        moduleAssembly.addServices( AuthenticationService.class, MemoryEntityStoreService.class ).visibleIn( Visibility.layer ).instantiateOnStartup();
    }
}
