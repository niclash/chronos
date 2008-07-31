package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.assembler.LayerAssembler;
import org.qi4j.chronos.ui.wicket.admin.assembler.AdminAssembly;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.view.ViewAssembler;

/**
 * @author edward.yakop@gmail.com
 */
public final class WicketLayerAssembler
    implements LayerAssembler
{
    public static final String LAYER_WICKET = "Wicket";

    public static final String MODULE_BOOTSTRAP = "bootstrap";
    public static final String MODULE_ADMIN = "admin";
    private static final String MODULE_VIEW = "view";

    public final LayerAssembly createLayerAssembly( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        LayerAssembly wicketLayer = anApplicationAssembly.newLayerAssembly( null );
        wicketLayer.setName( LAYER_WICKET );

        ModuleAssembly chronosBootstrap = wicketLayer.newModuleAssembly( MODULE_BOOTSTRAP );
        chronosBootstrap.addAssembler( new BootstrapModuleAssembler() );

        ModuleAssembly admin = wicketLayer.newModuleAssembly( MODULE_ADMIN );
        admin.addAssembler( new AdminAssembly() );

        ModuleAssembly view = wicketLayer.newModuleAssembly( MODULE_VIEW );
        view.addAssembler( new ViewAssembler() );

        return wicketLayer;
    }
}
