package org.qi4j.chronos.ui.contact;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactEntity;
import org.qi4j.chronos.model.Contact;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.entity.Identity;

public class ContactDataProvider extends AbstractSortableDataProvider<Contact>
{
    private static final long serialVersionUID = 1L;

    private IModel<HasContacts> hasContacts;

    public ContactDataProvider( IModel<HasContacts> hasContacts )
    {
        this.hasContacts = hasContacts;
    }

    public IModel<Contact> load( final String s )
    {
        return new ChronosDetachableModel<Contact>( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, ContactEntity.class ) );
    }

    public List<Contact> dataList( int first, int count )
    {
        //TODO
        List<Contact> contactEnties = new ArrayList<Contact>( hasContacts.getObject().contacts() );

        return contactEnties.subList( first, first + count );
    }

    public int size()
    {
        return hasContacts.getObject().contacts().size();
    }
}
