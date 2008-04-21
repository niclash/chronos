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
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.entity.Identity;

public abstract class ContactPersonTable<T extends HasContactPersons> extends ActionTable<ContactPerson, String>
{
    private ContactPersonDataProvider provider;

    public ContactPersonTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<ContactPerson>( "Delete" )
        {
            public void performAction( List<ContactPerson> contactPersons )
            {
                // TODO
//                getContactPersonService().delete( contactPersons );

                info( "Selected contact person(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<ContactPerson>( "Disable login" )
        {
            public void performAction( List<ContactPerson> contactPersons )
            {
                // TODO
//                getContactPersonService().enableLogin( false, contactPersons );

                info( "Selected contact person(s) are disabled login." );
            }
        } );

        addAction( new SimpleAction<ContactPerson>( "Enable login" )
        {
            public void performAction( List<ContactPerson> contactPersons )
            {
                // TODO
//                getContactPersonService().enableLogin( true, contactPersons );

                info( "Selected contact person(s) are enabled login." );
            }
        } );
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public AbstractSortableDataProvider<ContactPerson, String> getDetachableDataProvider()
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

    public void populateItems( Item item, ContactPerson obj )
    {
        final String contactPersonId = ( (Identity) obj).identity().get();

        item.add( createDetailLink( "firstName", obj.firstName().get(), contactPersonId ) );

        item.add( createDetailLink( "lastName", obj.lastName().get(), contactPersonId ) );

        item.add( new Label( "loginId", obj.login().get().name().get() ) );

        CheckBox loginEnabled = new CheckBox( "loginEnabled", new Model( obj.login().get().isEnabled().get() ) );

        loginEnabled.setEnabled( false );

        item.add( loginEnabled );

        SimpleLink editLink = createEditLink( contactPersonId );

        item.add( editLink );
    }

/*
    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }
*/

    private SimpleLink createEditLink( final String contactPersonId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( new ContactPersonEditPage( this.getPage() )
                {
                    public Customer getCustomer()
                    {
                        return ContactPersonTable.this.getCustomer();
                    }

                    public ContactPerson getContactPerson()
                    {
                        for( ContactPerson contactPerson : getCustomer().contactPersons() )
                        {
                            if( contactPersonId.equals( contactPerson.identity().get() ) )
                            {
                                return contactPerson;
                            }
                        }

                        return null;
                        // TODO
//                        return getContactPersonService().get( contactPersonId );
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
                    public ContactPerson getContactPerson()
                    {
                        for( ContactPerson contactPerson : getCustomer().contactPersons() )
                        {
                            if( contactPersonId.equals( contactPerson.identity().get() ) )
                            {
                                return contactPerson;
                            }
                        }

                        return null;
                        // TODO
//                        return getContactPersonService().get( contactPersonId );
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

    public abstract Customer getCustomer();
}
