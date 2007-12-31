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
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public abstract class ContactPersonTable<T extends HasContactPersons> extends ActionTable<ContactPersonEntityComposite, String>
{
    private ContactPersonDataProvider provider;

    public ContactPersonTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<ContactPersonEntityComposite>( "Delete" )
        {
            public void performAction( List<ContactPersonEntityComposite> contactPersons )
            {
                getContactPersonService().delete( contactPersons );

                info( "Selected contact person(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<ContactPersonEntityComposite>( "Disable login" )
        {
            public void performAction( List<ContactPersonEntityComposite> contactPersons )
            {
                getContactPersonService().enableLogin( false, contactPersons );

                info( "Selected contact person(s) are disabled login." );
            }
        } );

        addAction( new SimpleAction<ContactPersonEntityComposite>( "Enable login" )
        {
            public void performAction( List<ContactPersonEntityComposite> contactPersons )
            {
                getContactPersonService().enableLogin( true, contactPersons );

                info( "Selected contact person(s) are enabled login." );
            }
        } );
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
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
        final String contactPersonId = obj.identity().get();

        item.add( createDetailLink( "firstName", obj.getFirstName(), contactPersonId ) );

        item.add( createDetailLink( "lastName", obj.getLastName(), contactPersonId ) );

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        CheckBox loginEnabled = new CheckBox( "loginEnabled", new Model( obj.getLogin().getEnabled() ) );

        loginEnabled.setEnabled( false );

        item.add( loginEnabled );

        SimpleLink editLink = createEditLink( contactPersonId );

        item.add( editLink );
    }

    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }

    private SimpleLink createEditLink( final String contactPersonId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( new ContactPersonEditPage( this.getPage() )
                {
                    public CustomerEntityComposite getCustomer()
                    {
                        return ContactPersonTable.this.getCustomer();
                    }

                    public ContactPersonEntityComposite getContactPerson()
                    {
                        return getContactPersonService().get( contactPersonId );
                    }
                } );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private SimpleLink createDetailLink( String id, String text, final String contactPersonId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                ContactPersonDetailPage detailPage = new ContactPersonDetailPage( this.getPage() )
                {
                    public ContactPersonEntityComposite getContactPerson()
                    {
                        return getContactPersonService().get( contactPersonId );
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
