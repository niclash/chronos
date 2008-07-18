package org.qi4j.chronos.ui.wicket.authentication.assembler;

import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.ui.wicket.authentication.RoleHelperComposite;
import static org.qi4j.structure.Visibility.layer;

/**
 * {@code WicketAuthenticationModuleInitializer} initialize wicket authentication module.
 *
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public final class WicketAuthenticationModuleInitializer
    implements Assembler
{
    public WicketAuthenticationModuleInitializer()
    {
    }

    public final void assemble( ModuleAssembly aModule )
        throws AssemblyException
    {
        aModule.addComposites(
            RoleHelperComposite.class
        ).visibleIn( layer );
    }
}
