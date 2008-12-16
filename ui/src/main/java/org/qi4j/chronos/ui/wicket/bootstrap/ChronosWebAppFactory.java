package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.chronos.model.assembler.DomainLayerAssembler;
import static org.qi4j.chronos.ui.wicket.bootstrap.WicketLayerAssembler.LAYER_WICKET;
import static org.qi4j.chronos.ui.wicket.bootstrap.WicketLayerAssembler.MODULE_BOOTSTRAP;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.infrastructure.InfrastructureAssembler;
import org.qi4j.chronos.ui.wicket.bootstrap.assembler.service.ServiceLayerAssembler;
import org.qi4j.api.object.ObjectBuilder;
import org.qi4j.api.object.ObjectBuilderFactory;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;

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
        Module bootstrapModule = instance.findModule( LAYER_WICKET, MODULE_BOOTSTRAP );

        // Initialize dummy data
        ObjectBuilderFactory builderFactory = bootstrapModule.objectBuilderFactory();
        DummyDataInitializer dataInitializer = builderFactory.newObject( DummyDataInitializer.class );
        dataInitializer.initializeDummyData();

        // Create new chronos app
        ObjectBuilder<ChronosWebApp> appBuilder = builderFactory.newObjectBuilder( ChronosWebApp.class );
        return appBuilder.newInstance();
    }

    private Application newChronosQi4jApplication()
    {
        try
        {
            Application instance = newChronosApplication();
            instance.activate();
            return instance;
        }
        catch( Throwable e )
        {
            throw new WicketRuntimeException( "Chronos web app instantiation fail", e );
        }
    }

    private Application newChronosApplication()
        throws AssemblyException
    {
        Energy4Java boot = new Energy4Java();
        ApplicationAssembly chronosAppAssembly = boot.newApplicationAssembly();

        LayerAssembly infrastructure = createInfraLayer( chronosAppAssembly );

        LayerAssembly domain = createDomainLayer( chronosAppAssembly );
        domain.uses( infrastructure );

        LayerAssembly service = createServiceLayer( chronosAppAssembly );
        service.uses( infrastructure );
        service.uses( domain );

        LayerAssembly wicket = createWicketLayer( chronosAppAssembly );
        wicket.uses( infrastructure );
        wicket.uses( domain );
        wicket.uses( service );

        return boot.newApplication( chronosAppAssembly );
    }

    private LayerAssembly createWicketLayer( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        WicketLayerAssembler wicketLayerAssembler = new WicketLayerAssembler();
        return wicketLayerAssembler.createLayerAssembly( anApplicationAssembly );
    }

    private LayerAssembly createServiceLayer( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        ServiceLayerAssembler serviceAssembler = new ServiceLayerAssembler();
        return serviceAssembler.createLayerAssembly( anApplicationAssembly );
    }

    private LayerAssembly createDomainLayer( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        DomainLayerAssembler domainLayerAssembler = new DomainLayerAssembler();
        return domainLayerAssembler.createLayerAssembly( anApplicationAssembly );
    }

    private LayerAssembly createInfraLayer( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        InfrastructureAssembler infraLayerAssembler = new InfrastructureAssembler();
        return infraLayerAssembler.createLayerAssembly( anApplicationAssembly );
    }
}