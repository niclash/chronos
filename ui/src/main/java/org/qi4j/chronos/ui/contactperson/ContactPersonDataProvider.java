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
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ContactPersonDataProvider extends AbstractSortableDataProvider<ContactPersonEntityComposite, String>
{
    public String getId( ContactPersonEntityComposite contactPersonEntityComposite )
    {
        return contactPersonEntityComposite.getIdentity();
    }

    public ContactPersonEntityComposite load( String id )
    {
        return getContactPersonService().get( id );
    }

    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }

    public List<ContactPersonEntityComposite> dataList( int first, int count )
    {
        return getContactPersonService().findAll( getProjectOwner(), new FindFilter( first, count ) );
    }

    public int getSize()
    {
        return getContactPersonService().countAll( getProjectOwner() );
    }

    public abstract ProjectOwnerEntityComposite getProjectOwner();
}
