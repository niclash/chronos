/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.contact;

import java.util.List;
import java.util.ArrayList;
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.library.general.model.Contact;

public abstract class ContactDataProvider<T extends HasContacts> extends AbstractSortableDataProvider<Contact, String>
{
    public int getSize()
    {
        return getHasContacts().contacts().size();
        // TODO
//        return getContactService().countAll( getHasContacts() );
    }

    private ContactService getContactService()
    {
        return ChronosWebApp.getServices().getContactService();
    }

    public String getId( Contact t )
    {
        return t.contactValue().get();
    }

    public Contact load( String s )
    {
        return getContactService().get( getHasContacts(), s );
    }

    public List<Contact> dataList( int first, int count )
    {
        return new ArrayList<Contact>( getHasContacts().contacts() );
        // TODO
//        return getContactService().findAll( getHasContacts(), new FindFilter( first, count ) );
    }

    public abstract T getHasContacts();
}
