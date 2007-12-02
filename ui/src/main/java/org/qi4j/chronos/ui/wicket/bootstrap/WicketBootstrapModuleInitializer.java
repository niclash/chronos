package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.MODULE_NAME_WICKET_BOOTSTRAP;
import static org.qi4j.composite.NullArgumentException.validateNotNull;

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
    }
}
