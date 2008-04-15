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
package org.qi4j.chronos.ui.contactperson;

import java.util.List;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ContactPersonDataProvider<T extends HasContactPersons> extends AbstractSortableDataProvider<ContactPerson, String>
{
    public String getId( ContactPerson contactPerson )
    {
        return ( (ContactPersonEntityComposite) contactPerson).identity().get();
    }

    public ContactPerson load( String id )
    {
        return getContactPersonService().get( id );
    }

    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }

    public List<ContactPerson> dataList( int first, int count )
    {
        return getContactPersonService().findAll( getHasContactPersons(), new FindFilter( first, count ) );
    }

    public int getSize()
    {
        return getContactPersonService().countAll( getHasContactPersons() );
    }

    public abstract T getHasContactPersons();
}
