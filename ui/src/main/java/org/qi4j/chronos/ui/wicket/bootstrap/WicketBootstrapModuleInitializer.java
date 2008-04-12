package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.service.authentication.AuthenticationConfiguration;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.*;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;

/**
 * @author edward.yakop@gmail.com
 */
public final class WicketBootstrapModuleInitializer
{
    private WicketBootstrapModuleInitializer()
    {
    }

    public static void addWicketBootstrapModule( LayerAssembly aLayerAssembly )
    {
        validateNotNull( "aLayerAssembly", aLayerAssembly );
        ModuleAssembly moduleAssembly = aLayerAssembly.newModuleAssembly();
        moduleAssembly.setName( MODULE_NAME_WICKET_BOOTSTRAP );
        try
        {
            moduleAssembly.addObjects(
                ChronosWebApp.class,
                ChronosPageFactory.class,
                ChronosSession.class,
                LoginPage.class
            );
            moduleAssembly.addComposites(
                AuthenticationConfiguration.class
            );
            moduleAssembly.addServices(
                AuthenticationService.class,
                UuidIdentityGeneratorService.class,
                MemoryEntityStoreService.class
            );
        }
        catch( AssemblyException e )
        {
            System.err.println( e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
