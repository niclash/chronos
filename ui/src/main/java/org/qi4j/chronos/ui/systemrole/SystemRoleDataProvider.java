package org.qi4j.chronos.ui.systemrole;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasSystemRoles;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class SystemRoleDataProvider extends AbstractSortableDataProvider<SystemRole>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasSystemRoles> hasSystemRoles;

    public SystemRoleDataProvider( IModel<? extends HasSystemRoles> hasSystemRoles )
    {
        this.hasSystemRoles = hasSystemRoles;
    }

    public IModel<SystemRole> load( String id )
    {
        SystemRole systemRole = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, SystemRole.class );

        return new ChronosDetachableModel<SystemRole>( systemRole );
    }

    public List<SystemRole> dataList( int first, int count )
    {
        //TODO
        List<SystemRole> systemRoles = new ArrayList<SystemRole>( hasSystemRoles.getObject().systemRoles() );

        return systemRoles;
    }

    public int size()
    {
        return hasSystemRoles.getObject().systemRoles().size();
    }
}
