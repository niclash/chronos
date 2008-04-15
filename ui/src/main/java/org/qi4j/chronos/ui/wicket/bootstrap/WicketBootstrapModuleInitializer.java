package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.service.authentication.AuthenticationConfiguration;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.chronos.service.account.AccountServiceConfiguration;
import org.qi4j.chronos.service.lab.LoginServiceComposite;
import org.qi4j.chronos.service.lab.AdminServiceComposite;
import org.qi4j.chronos.service.lab.SystemRoleServiceComposite;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.*;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
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
                AuthenticationConfiguration.class,
                AccountServiceConfiguration.class,
                AccountEntityComposite.class,
                AdminEntityComposite.class,
                LoginEntityComposite.class,
                AddressEntityComposite.class,
                CityEntityComposite.class,
                StateEntityComposite.class,
                CountryEntityComposite.class,
                SystemRoleEntityComposite.class
            );
            moduleAssembly.addServices(
                AccountServiceComposite.class,
                AdminServiceComposite.class,
                LoginServiceComposite.class,
                AuthenticationService.class,
                UuidIdentityGeneratorService.class,
                MemoryEntityStoreService.class,
                SystemRoleServiceComposite.class
            );
        }
        catch( AssemblyException e )
        {
            System.err.println( e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
