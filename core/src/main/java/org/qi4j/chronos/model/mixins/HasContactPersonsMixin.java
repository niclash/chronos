package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.associations.HasContactPersons;

public class HasContactPersonsMixin implements HasContactPersons
{
    private List<ContactPerson> list;

    public HasContactPersonsMixin()
    {
        list = new ArrayList<ContactPerson>();
    }

    public Iterator<ContactPerson> contactPersonIterator()
    {
        return list.iterator();
    }

    public void addContactPerson( ContactPerson contactPerson )
    {
        list.add( contactPerson );
    }

    public void removeContactPerson( ContactPerson contactPerson )
    {
        list.remove( contactPerson );
    }
}
