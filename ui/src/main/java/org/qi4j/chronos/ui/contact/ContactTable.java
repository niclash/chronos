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
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class ContactTable extends ActionTable<ContactComposite, String>
{
    public ContactTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<ContactComposite, String> getDetachableDataProvider()
    {
        return new ContactDataProvider<ContactPersonEntityComposite>()
        {
            public ContactPersonEntityComposite getHasContacts()
            {
                return ContactTable.this.getContactPerson();
            }
        };
    }

    public void populateItems( Item item, ContactComposite obj )
    {
        final String contactValue = obj.getContactValue();

        item.add( new Label( "contact", obj.getContactValue() ) );
        item.add( new Label( "contactType", obj.getContactType() ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ContactEditPage editPage = new ContactEditPage( (BasePage) this.getPage() )
                {
                    public ContactComposite getContact()
                    {
                        return ChronosWebApp.getServices().getContactService().get( ContactTable.this.getContactPerson(), contactValue );
                    }

                    public ContactPersonEntityComposite getContactPerson()
                    {
                        return ContactTable.this.getContactPerson();
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Contact", "Contact Type", "" );
    }

    public abstract ContactPersonEntityComposite getContactPerson();
}
