package org.qi4j.chronos.ui.wicket.bootstrap;

import java.util.List;
import java.util.ArrayList;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.ApplicationFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.service.lab.AdminServiceComposite;
import org.qi4j.chronos.service.lab.LoginServiceComposite;
import org.qi4j.chronos.service.lab.SystemRoleServiceComposite;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.composite.ObjectBuilderFactory;
import org.qi4j.runtime.Energy4Java;
import org.qi4j.runtime.Qi4jRuntime;
import org.qi4j.runtime.structure.ApplicationContext;
import org.qi4j.runtime.structure.ApplicationInstance;
import org.qi4j.runtime.structure.LayerContext;
import org.qi4j.runtime.structure.LayerInstance;
import org.qi4j.runtime.structure.ModuleContext;
import org.qi4j.runtime.structure.ModuleInstance;
import org.qi4j.spi.structure.LayerBinding;
import org.qi4j.spi.structure.LayerModel;
import org.qi4j.spi.structure.LayerResolution;
import org.qi4j.spi.structure.ModuleBinding;
import org.qi4j.spi.structure.ModuleModel;
import org.qi4j.spi.structure.ModuleResolution;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.Identity;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.service.ServiceLocator;

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

        UnitOfWorkFactory factory = bootstrapModule.getStructureContext().getUnitOfWorkFactory();
        ServiceLocator locator = bootstrapModule.getStructureContext().getServiceLocator();
        initDummyData( factory, locator );

        ObjectBuilderFactory builderFactory = bootstrapModule.getStructureContext().getObjectBuilderFactory();
        ObjectBuilder<ChronosWebApp> builder = builderFactory.newObjectBuilder( ChronosWebApp.class );

        return builder.newInstance();
    }

    private void initDummyData( UnitOfWorkFactory factory, ServiceLocator locator )
    {
        UnitOfWork unitOfWork = factory.newUnitOfWork();

        AccountService accountService = locator.lookupService( AccountServiceComposite.class ).get();
        AccountEntityComposite jayway = unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
        jayway.name().set( "Jayway Malaysia" );
        jayway.reference().set( "Jayway Malaysia Sdn Bhd" );
        jayway.isEnabled().set( true );

        AccountEntityComposite test = unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
        test.name().set( "Test Corp" );
        test.reference().set( "Test Corporation" );
        test.isEnabled().set( true );

        Address address = unitOfWork.newEntityBuilder( AddressEntityComposite.class ).newInstance();
        City city = unitOfWork.newEntityBuilder( CityEntityComposite.class ).newInstance();
        State state = unitOfWork.newEntityBuilder( StateEntityComposite.class ).newInstance();
        Country country = unitOfWork.newEntityBuilder( CountryEntityComposite.class ).newInstance();

        address.firstLine().set( "AbcMixin Road" );
        address.secondLine().set( "Way Cente" );
        address.zipCode().set( "111" );
        country.name().set( "Malaysia" );
        state.name().set( "Wilayah" );
        city.name().set( "KL" );
        city.country().set( country );
        city.state().set( state );

        address.city().set( city );

        jayway.address().set( address );
        test.address().set( address );

        accountService.add( jayway );
        accountService.add( test );

        try
        {
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
        }

        unitOfWork = factory.newUnitOfWork();

        LoginServiceComposite loginService = locator.lookupService( LoginServiceComposite.class ).get();

        Login adminLogin = loginService.newInstance( unitOfWork, LoginEntityComposite.class );
        String loginId = ( (Identity) adminLogin).identity().get();
        adminLogin.isEnabled().set( true );
        adminLogin.name().set( "admin" );
        adminLogin.password().set( "admin" );

        try
        {
            loginService.save( unitOfWork, adminLogin );
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
        }

        unitOfWork = factory.newUnitOfWork();

        List<SystemRole> roles = new ArrayList<SystemRole>();
        SystemRoleServiceComposite roleService = locator.lookupService( SystemRoleServiceComposite.class ).get();

        SystemRole adminRole = roleService.newInstance( unitOfWork );
        adminRole.name().set( SystemRole.SYSTEM_ADMIN );
        adminRole.systemRoleType().set( SystemRoleTypeEnum.ADMIN );
        roles.add( adminRole );

        SystemRole accountAdmin = roleService.newInstance( unitOfWork );
        accountAdmin.name().set( SystemRole.ACCOUNT_ADMIN );
        accountAdmin.systemRoleType().set( SystemRoleTypeEnum.STAFF );
        roles.add( accountAdmin );

        SystemRole developer = roleService.newInstance( unitOfWork );
        developer.name().set( SystemRole.ACCOUNT_DEVELOPER );
        developer.systemRoleType().set( SystemRoleTypeEnum.STAFF );
        roles.add( developer );

        SystemRole contactPerson = roleService.newInstance( unitOfWork );
        contactPerson.name().set( SystemRole.CONTACT_PERSON );
        contactPerson.systemRoleType().set( SystemRoleTypeEnum.CONTACT_PERSON );
        roles.add( contactPerson );

        try
        {
             roleService.saveAll( unitOfWork, roles );
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
        }

        unitOfWork = factory.newUnitOfWork();
        
        AdminServiceComposite adminService = locator.lookupService( AdminServiceComposite.class ).get();

        Admin admin = adminService.newInstance( unitOfWork, AdminEntityComposite.class );
        admin.firstName().set( "admin" );
        admin.lastName().set( "admin" );
        admin.gender().set( GenderType.MALE );
        admin.login().set( loginService.get( unitOfWork, loginId ) );

        for( SystemRole role : roleService.findAll( unitOfWork ) )
        {
            admin.systemRoles().add( role );
        }

        try
        {
            adminService.save( unitOfWork, admin );
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
        }

        unitOfWork = factory.newUnitOfWork();
    }

    private ModuleInstance getBootstrapModule( LayerInstance wicketLayer )
    {
        List<ModuleInstance> moduleInstances = wicketLayer.getModuleInstances();
        ModuleInstance bootstrapModule = null;
        for( ModuleInstance moduleInstance : moduleInstances )
        {
            ModuleContext moduleContext = moduleInstance.getModuleContext();
            ModuleBinding moduleBinding = moduleContext.getModuleBinding();
            ModuleResolution moduleResolution = moduleBinding.getModuleResolution();
            ModuleModel moduleModel = moduleResolution.getModuleModel();
            String moduleName = moduleModel.getName();
            if( MODULE_NAME_WICKET_BOOTSTRAP.equals( moduleName ) )
            {
                bootstrapModule = moduleInstance;
                break;
            }
        }
        return bootstrapModule;
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
