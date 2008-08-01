package org.qi4j.chronos.ui.customer;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.associations.HasCustomers;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class CustomerDataProvider extends AbstractSortableDataProvider<Customer>
{
    private static final long serialVersionUID = 1L;

    private final IModel<HasCustomers> hasCustomers;

    public CustomerDataProvider( IModel<HasCustomers> hasCustomers )
    {
        this.hasCustomers = hasCustomers;
    }

    public IModel<Customer> load( String id )
    {
        return new ChronosDetachableModel<Customer>( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, Customer.class ) );
    }

    public List<Customer> dataList( int first, int count )
    {
        //TODO
        List<Customer> customerModels = new ArrayList<Customer>( hasCustomers.getObject().customers() );

        return customerModels.subList( first, first + count );
    }

    public int size()
    {
        return hasCustomers.getObject().customers().size();
    }
}
