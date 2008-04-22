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
import org.qi4j.chronos.service.AggregatedServiceComposite;
import org.qi4j.chronos.service.task.TaskServiceComposite;
import org.qi4j.chronos.service.project.ProjectServiceComposite;
import org.qi4j.chronos.service.relationship.RelationshipServiceComposite;
import org.qi4j.chronos.service.customer.CustomerServiceComposite;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import org.qi4j.chronos.ui.admin.AdminHomePage;
import org.qi4j.chronos.ui.account.AccountListPage;
import org.qi4j.chronos.ui.account.AccountAddPage;
import org.qi4j.chronos.ui.account.AccountDetailPage;
import org.qi4j.chronos.ui.account.AccountEditPage;
import org.qi4j.chronos.ui.account.AccountDataProvider;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.chronos.ui.staff.StaffListPage;
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
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.entity.memory.IndexedMemoryEntityStoreService;
import org.qi4j.entity.index.rdf.RDFQueryService;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;
import org.qi4j.structure.Visibility;

import static org.qi4j.chronos.ui.wicket.bootstrap.Constants.MODULE_NAME_WICKET_BOOTSTRAP;
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.project.ProjectAddPage;
import org.qi4j.chronos.ui.pricerate.PriceRateScheduleListPage;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.contactperson.ContactPersonHomePage;
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

        ModuleAssembly accountModule = aLayerAssembly.newModuleAssembly();
        accountModule.setName( "Account Module" );
        ModuleAssembly projectModule = aLayerAssembly.newModuleAssembly();
        projectModule.setName( "Project Module" );
        ModuleAssembly otherModule = aLayerAssembly.newModuleAssembly();
        otherModule.setName( "Other Module" );

        // TODO kamil: make it into separate module/assembler
        try
        {
            accountModule.addObjects( AccountListPage.class,
                                      AccountAddPage.class,
                                      AccountDetailPage.class,
                                      AccountEditPage.class,
                                      AccountDataProvider.class
            ).visibleIn( Visibility.application );
/*
            accountModule.addComposites( AccountServiceConfiguration.class ).visibleIn( Visibility.application );
            accountModule.addServices( AccountServiceComposite.class );
*/

            projectModule.addObjects(
                ProjectListPage.class,
                ProjectAddPage.class
            ).visibleIn( Visibility.application );

            otherModule.addObjects(
                PriceRateScheduleListPage.class,
                CustomerListPage.class,
                StaffListPage.class,
                ProjectRoleListPage.class,

                ContactPersonHomePage.class
            ).visibleIn( Visibility.application );
        }
        catch( AssemblyException ae )
        {
            System.err.println( ae.getLocalizedMessage() );
            ae.printStackTrace();
        }

        ModuleAssembly moduleAssembly = aLayerAssembly.newModuleAssembly();
        moduleAssembly.setName( MODULE_NAME_WICKET_BOOTSTRAP );
        try
        {
            moduleAssembly.addObjects(
                AdminHomePage.class,
                SystemRoleListPage.class,
                StaffHomePage.class,
                ChronosWebApp.class,
                ChronosPageFactory.class,
                ChronosSession.class,
                LoginPage.class,

                DummyDataInitializer.class
            ).visibleIn( Visibility.application );
            moduleAssembly.addComposites(
                AdminEntityComposite.class,
                ContactPersonEntityComposite.class,
                LoginEntityComposite.class,
                AddressEntityComposite.class,
                CityEntityComposite.class,
                StateEntityComposite.class,
                CountryEntityComposite.class,
                MoneyEntityComposite.class,
                StaffEntityComposite.class,
                SystemRoleServiceConfiguration.class,
                SystemRoleEntityComposite.class,
                ProjectRoleEntityComposite.class,
                PriceRateEntityComposite.class,
                PriceRateScheduleEntityComposite.class,
                CustomerEntityComposite.class,
                ContactPersonEntityComposite.class,
                ContactEntityComposite.class,
                RelationshipEntityComposite.class,
                ProjectEntityComposite.class,
                TaskEntityComposite.class,
                ProjectAssigneeEntityComposite.class,
                TimeRangeEntityComposite.class,
                AccountEntityComposite.class,
                LegalConditionEntityComposite.class,
                OngoingWorkEntryEntityComposite.class,
                WorkEntryEntityComposite.class,
                CommentEntityComposite.class,

                AccountServiceConfiguration.class,
                UserServiceConfiguration.class
            ).visibleIn( Visibility.application );
            moduleAssembly.addServices(
                AccountServiceComposite.class,
                AdminServiceComposite.class,
                LoginServiceComposite.class,
                UserServiceComposite.class,
                UuidIdentityGeneratorService.class,
                IndexedMemoryEntityStoreService.class,
                RDFQueryService.class,

                CustomerServiceComposite.class,
                TaskServiceComposite.class,
                ProjectServiceComposite.class,
                SystemRoleServiceComposite.class,
                RelationshipServiceComposite.class,
                AuthenticationServiceComposite.class,
                AggregatedServiceComposite.class
            ).visibleIn( Visibility.application );
        }
        catch( AssemblyException e )
        {
            System.err.println( e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
