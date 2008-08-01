package org.qi4j.chronos.ui.staff;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.associations.HasStaffs;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class StaffDataProvider extends AbstractSortableDataProvider<Staff>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasStaffs> hasStaffs;

    public StaffDataProvider( IModel<? extends HasStaffs> hasStaffs )
    {
        this.hasStaffs = hasStaffs;
    }

    public IModel<Staff> load( String id )
    {
        Staff staff = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, Staff.class );

        return new ChronosDetachableModel<Staff>( staff );
    }

    public List<Staff> dataList( int first, int count )
    {
        //TODO
        List<Staff> staffs = new ArrayList<Staff>( hasStaffs.getObject().staffs() );

        return staffs.subList( first, count );
    }

    public int size()
    {
        return hasStaffs.getObject().staffs().size();
    }
}
