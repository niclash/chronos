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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class ContactPersonTable<T extends HasContactPersons> extends ActionTable<ContactPersonEntityComposite, String>
{
    private ContactPersonDataProvider provider;

    public ContactPersonTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<ContactPersonEntityComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new ContactPersonDataProvider()
            {
                public T getHasContactPersons()
                {
                    return ContactPersonTable.this.getHasContactPersons();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, ContactPersonEntityComposite obj )
    {
        final String contactPersonId = obj.getIdentity();

        item.add( createDetailLink( "firstName", obj.getFirstName(), contactPersonId ) );

        item.add( createDetailLink( "lastName", obj.getLastName(), contactPersonId ) );

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        CheckBox loginEnabled = new CheckBox( "loginEnabled", new Model( obj.getLogin().isEnabled() ) );

        loginEnabled.setEnabled( false );
        item.add( loginEnabled );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( new ContactPersonEditPage( (BasePage) this.getPage() )
                {
                    public CustomerEntityComposite getCustomer()
                    {
                        return ContactPersonTable.this.getCustomer();
                    }

                    public ContactPersonEntityComposite getContactPerson()
                    {
                        return ChronosWebApp.getServices().getContactPersonService().get( contactPersonId );
                    }
                } );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String text, final String contactPersonId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                ContactPersonDetailPage detailPage = new ContactPersonDetailPage( (BasePage) this.getPage() )
                {
                    public ContactPersonEntityComposite getContactPerson()
                    {
                        return ChronosWebApp.getServices().getContactPersonService().get( contactPersonId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Login Id", "Login Enabled", "Edit" );
    }

    public abstract T getHasContactPersons();

    public abstract CustomerEntityComposite getCustomer();
}
