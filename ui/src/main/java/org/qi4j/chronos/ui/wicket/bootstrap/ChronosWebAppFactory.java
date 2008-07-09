package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import static org.qi4j.chronos.ui.wicket.Constants.LAYER_NAME_WICKET;
import static org.qi4j.chronos.ui.wicket.WicketLayerAssemblyInitializer.addWicketLayerAssembly;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.MODULE_NAME_WICKET_BOOTSTRAP;
import org.qi4j.object.ObjectBuilder;
import org.qi4j.object.ObjectBuilderFactory;
import org.qi4j.structure.Application;
import org.qi4j.structure.Module;

/**
 * {@code ChronosWebAppFactory} responsibles to create a chronos web application factory.
 *
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class ChronosWebAppFactory
    implements IWebApplicationFactory
{
    public final WebApplication createApplication( WicketFilter aFilter )
    {
        Application instance = newChronosQi4jApplication();
        Module bootstrapModule = instance.findModule( LAYER_NAME_WICKET, MODULE_NAME_WICKET_BOOTSTRAP );

        // Initialize dummy data
        ObjectBuilderFactory builderFactory = bootstrapModule.objectBuilderFactory();
        ObjectBuilder<DummyDataInitializer> initializerBuilder = builderFactory.newObjectBuilder( DummyDataInitializer.class );
        initializerBuilder.newInstance().initializeDummyData();

        // Create new chronos app
        ObjectBuilder<ChronosWebApp> appBuilder = builderFactory.newObjectBuilder( ChronosWebApp.class );
        return appBuilder.newInstance();
    }

    private Application newChronosQi4jApplication()
    {
        Application instance;
        try
        {
            instance = newChronosApplication();
            instance.activate();
        }
        catch( Exception e )
        {
            throw new IllegalStateException( "Could not activate application", e );
        }
        return instance;
    }

    private Application newChronosApplication()
        throws AssemblyException

    {
        Energy4Java boot = new Energy4Java();
        ApplicationAssembly chronosAppAssembly = boot.newApplicationAssembly();
        addWicketLayerAssembly( chronosAppAssembly );
        return boot.newApplication( chronosAppAssembly );
    }
}
