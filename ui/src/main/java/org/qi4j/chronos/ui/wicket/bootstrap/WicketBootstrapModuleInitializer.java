package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import org.qi4j.chronos.ui.wicket.bootstrap.serialization.Qi4jObjectStreamFactory;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.InfrastructureAssembler;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.DomainAssembler;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.ViewAssembler;
import org.qi4j.structure.Visibility;

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

    public static void addWicketBootstrapModule( LayerAssembly aLayerAssembly ) throws AssemblyException
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
                LoginPage.class,
                Qi4jObjectStreamFactory.class,
                DummyDataInitializer.class
            ).visibleIn( Visibility.application );
            moduleAssembly.addAssembler( new InfrastructureAssembler() );
            moduleAssembly.addAssembler( new DomainAssembler() );
            moduleAssembly.addAssembler( new ViewAssembler() );
        }
        catch( AssemblyException e )
        {
            System.err.println( e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
