package org.qi4j.chronos.ui.contactperson;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class ContactPersonDataProvider extends AbstractSortableDataProvider<ContactPerson>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasContactPersons> hasContactPersons;

    public ContactPersonDataProvider( IModel<? extends HasContactPersons> hasContactPersons )
    {
        this.hasContactPersons = hasContactPersons;
    }

    public IModel<ContactPerson> load( String id )
    {
        return new ChronosDetachableModel<ContactPerson>( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, ContactPerson.class ) );
    }

    public List<ContactPerson> dataList( int first, int count )
    {
        //TODO
        List<ContactPerson> contactPersons = new ArrayList<ContactPerson>( hasContactPersons.getObject().contactPersons() );

        return contactPersons.subList( first, first + count );
    }

    public int size()
    {
        return hasContactPersons.getObject().contactPersons().size();
    }
}
