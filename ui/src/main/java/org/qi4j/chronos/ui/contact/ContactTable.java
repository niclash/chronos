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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.library.general.model.Contact;

public abstract class ContactTable extends ActionTable<Contact, String>
{
    public ContactTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<Contact>( "Delete" )
        {
            public void performAction( List<Contact> contactComposites )
            {
                getContactService().deleteContact( getContactPerson(), contactComposites );

                info( "Selected contact(s) are deleted" );
            }
        } );
    }

    public AbstractSortableDataProvider<Contact, String> getDetachableDataProvider()
    {
        return new ContactDataProvider<ContactPerson>()
        {
            public ContactPerson getHasContacts()
            {
                return ContactTable.this.getContactPerson();
            }
        };
    }

    private ContactService getContactService()
    {
        return ChronosWebApp.getServices().getContactService();
    }

    public void populateItems( Item item, Contact obj )
    {
        final String contactValue = obj.contactValue().get();

        item.add( new Label( "contact", obj.contactValue().get() ) );
        item.add( new Label( "contactType", obj.contactType().get() ) );

        SimpleLink simpleLink = createEditLink( contactValue );

        item.add( simpleLink );
    }

    private SimpleLink createEditLink( final String contactValue )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ContactEditPage editPage = new ContactEditPage( (BasePage) this.getPage() )
                {
                    public Contact getContact()
                    {
                        return getContactService().get( ContactTable.this.getContactPerson(), contactValue );
                    }

                    public ContactPerson getContactPerson()
                    {
                        return ContactTable.this.getContactPerson();
                    }
                };

                setResponsePage( editPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Contact", "Contact Type", "" );
    }

    public abstract ContactPerson getContactPerson();
}
