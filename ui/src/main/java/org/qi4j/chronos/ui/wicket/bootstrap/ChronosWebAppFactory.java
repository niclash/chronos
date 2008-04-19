package org.qi4j.chronos.ui.wicket.bootstrap;

import java.util.List;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.ApplicationFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.chronos.service.systemrole.SystemRoleServiceComposite;
import org.qi4j.chronos.service.systemrole.SystemRoleService;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.service.user.UserServiceComposite;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.authentication.AuthenticationServiceComposite;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;
import org.qi4j.runtime.Energy4Java;
import org.qi4j.runtime.Qi4jRuntime;
import org.qi4j.runtime.structure.ApplicationInstance;
import org.qi4j.runtime.structure.LayerContext;
import org.qi4j.runtime.structure.LayerInstance;
import org.qi4j.runtime.structure.ModuleContext;
import org.qi4j.runtime.structure.ModuleInstance;
import org.qi4j.runtime.structure.ApplicationContext;
import org.qi4j.spi.structure.LayerBinding;
import org.qi4j.spi.structure.LayerModel;
import org.qi4j.spi.structure.LayerResolution;
import org.qi4j.spi.structure.ModuleBinding;
import org.qi4j.spi.structure.ModuleModel;
import org.qi4j.spi.structure.ModuleResolution;
import org.qi4j.entity.UnitOfWorkFactory;

import static org.qi4j.chronos.ui.wicket.Constants.LAYER_NAME_WICKET;
import static org.qi4j.chronos.ui.wicket.WicketLayerAssemblyInitializer.addWicketLayerAssembly;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.MODULE_NAME_WICKET_BOOTSTRAP;

/**
 * {@code ChronosWebAppFactory} responsibles to create a chronos web application factory.
 *
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class ChronosWebAppFactory
    implements IWebApplicationFactory
{
    private static final String APPLICATION_NAME = "Chronos Qi4j application";

    public final WebApplication createApplication( WicketFilter aFilter )
    {
        ApplicationInstance instance;
        try
        {
            instance = newChronosApplication();
            instance.activate();
        }
        catch( Exception e )
        {
            throw new IllegalStateException( "Could not activate application", e );
        }
        LayerInstance wicketLayer = getWicketLayerInstance( instance );
        if( wicketLayer == null )
        {
            throw new IllegalStateException( "Layer [" + LAYER_NAME_WICKET + "] is not found." );
        }

        ModuleInstance bootstrapModule = getBootstrapModule( wicketLayer );
        if( bootstrapModule == null )
        {
            throw new IllegalStateException( "Module [" + MODULE_NAME_WICKET_BOOTSTRAP + "] is not found." );
        }

//        instance.getApplicationContext().getApplicationBinding().getApplicationResolution().getApplicationModel().
//        wicketLayer.lookupService(  );

        ModuleInstance accountModuleInstance = getModuleInstanceByName( wicketLayer, "Account Module" );

        UnitOfWorkFactory factory = bootstrapModule.getStructureContext().getUnitOfWorkFactory();
//        ServiceLocator locator = bootstrapModule.getStructureContext().getServiceLocator();
        SystemRoleService roleService = wicketLayer.lookupService( SystemRoleServiceComposite.class ).get();

//        AccountService accountService = accountModuleInstance.getStructureContext().getServiceLocator().lookupService( AccountServiceComposite.class ).get();
        AccountService accountService = wicketLayer.lookupService( AccountServiceComposite.class ).get();

        AuthenticationService authenticationService = wicketLayer.lookupService( AuthenticationServiceComposite.class ).get();
        UserService userService = wicketLayer.lookupService( UserServiceComposite.class ).get();

//        ObjectBuilderFactory builderFactory = bootstrapModule.getStructureContext().getObjectBuilderFactory();
        ObjectBuilderFactory builderFactory = accountModuleInstance.getStructureContext().getObjectBuilderFactory();
        ObjectBuilder<DummyDataInitializer> initializerBuilder = builderFactory.newObjectBuilder( DummyDataInitializer.class );
        initializerBuilder.use( factory, roleService, accountService, userService );
        initializerBuilder.newInstance().initializeDummyData();

        ObjectBuilder<ChronosWebApp> appBuilder = builderFactory.newObjectBuilder( ChronosWebApp.class );
        appBuilder.use( roleService, accountService, userService, authenticationService );

        return appBuilder.newInstance();
    }

    private ModuleInstance getBootstrapModule( LayerInstance wicketLayer )
    {
        return getModuleInstanceByName( wicketLayer, MODULE_NAME_WICKET_BOOTSTRAP );
    }

    private ModuleInstance getModuleInstanceByName( LayerInstance wicketLayer, String moduleName )
    {
        List<ModuleInstance> moduleInstances = wicketLayer.getModuleInstances();
        ModuleInstance instance = null;
        for( ModuleInstance moduleInstance : moduleInstances )
        {
            ModuleContext moduleContext = moduleInstance.getModuleContext();
            ModuleBinding moduleBinding = moduleContext.getModuleBinding();
            ModuleResolution moduleResolution = moduleBinding.getModuleResolution();
            ModuleModel moduleModel = moduleResolution.getModuleModel();
            String m_moduleName = moduleModel.getName();
            if( m_moduleName.equals( moduleName ) )
            {
                instance = moduleInstance;
                break;
            }
        }
        return instance;
    }

    private LayerInstance getWicketLayerInstance( ApplicationInstance instance )
    {
        List<LayerInstance> layers = instance.getLayerInstances();
        LayerInstance wicketLayer = null;
        for( LayerInstance layer : layers )
        {
            LayerContext context = layer.getLayerContext();
            LayerBinding layerBinding = context.getLayerBinding();
            LayerResolution layerResolution = layerBinding.getLayerResolution();
            LayerModel layerModel = layerResolution.getLayerModel();
            String layerName = layerModel.getName();

            if( LAYER_NAME_WICKET.equals( layerName ) )
            {
                wicketLayer = layer;
                break;
            }
        }

        return wicketLayer;
    }

    private ApplicationInstance newChronosApplication()
        throws AssemblyException

    {
        Qi4jRuntime is = new Energy4Java();
        ApplicationAssemblyFactory appAssemblyFactory = new ApplicationAssemblyFactory();
        ApplicationFactory appFactory = new ApplicationFactory( is, appAssemblyFactory );
        ApplicationAssembly chronosAppAssembly = new ApplicationAssembly();
        addWicketLayerAssembly( chronosAppAssembly );

        ApplicationContext context = appFactory.newApplication( chronosAppAssembly );
        return context.newApplicationInstance( APPLICATION_NAME );
    }
}
