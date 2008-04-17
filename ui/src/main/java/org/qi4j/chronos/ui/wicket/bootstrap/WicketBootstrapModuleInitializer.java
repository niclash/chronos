package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.service.authentication.AuthenticationServiceComposite;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.chronos.service.account.AccountServiceConfiguration;
import org.qi4j.chronos.service.lab.LoginServiceComposite;
import org.qi4j.chronos.service.lab.AdminServiceComposite;
import org.qi4j.chronos.service.systemrole.SystemRoleServiceComposite;
import org.qi4j.chronos.service.systemrole.SystemRoleServiceConfiguration;
import org.qi4j.chronos.service.user.UserServiceComposite;
import org.qi4j.chronos.service.user.UserServiceConfiguration;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.*;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.account.AccountListPage;
import org.qi4j.chronos.ui.account.AccountAddPage;
import org.qi4j.chronos.ui.account.AccountDetailPage;
import org.qi4j.chronos.ui.account.AccountEditPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;
import org.qi4j.chronos.ui.systemrole.StaffSystemRoleDataProvider;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;
import org.qi4j.structure.Visibility;

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
                DummyDataInitializer.class,
                AccountListPage.class,
                SystemRoleListPage.class,
                AdminHomePage.class,
                StaffHomePage.class,
                ChronosWebApp.class,
                ChronosPageFactory.class,
                ChronosSession.class,
                LoginPage.class,
                AccountAddPage.class,
                AccountDetailPage.class,
                AccountEditPage.class
            );
            moduleAssembly.addComposites(
                AccountServiceConfiguration.class,
                AccountEntityComposite.class,
                AdminEntityComposite.class,
                ContactPersonEntityComposite.class,
                LoginEntityComposite.class,
                AddressEntityComposite.class,
                CityEntityComposite.class,
                StateEntityComposite.class,
                CountryEntityComposite.class,
                MoneyEntityComposite.class,
                UserServiceConfiguration.class,
                StaffEntityComposite.class,
                SystemRoleServiceConfiguration.class,
                SystemRoleEntityComposite.class
            );
            moduleAssembly.addServices(
                AccountServiceComposite.class,
                AdminServiceComposite.class,
                LoginServiceComposite.class,
                UserServiceComposite.class,
                UuidIdentityGeneratorService.class,
                MemoryEntityStoreService.class,
                SystemRoleServiceComposite.class,
                AuthenticationServiceComposite.class
            ).visibleIn( Visibility.layer ).instantiateOnStartup();
        }
        catch( AssemblyException e )
        {
            System.err.println( e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
