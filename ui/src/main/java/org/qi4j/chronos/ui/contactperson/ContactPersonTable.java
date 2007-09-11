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
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class ContactPersonTable extends ActionTable<ContactPersonEntityComposite>
{
    private SimpleDataProvider<ContactPersonEntityComposite> provider;

    public ContactPersonTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<ContactPersonEntityComposite> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new SimpleDataProvider<ContactPersonEntityComposite>()
            {
                public EntityService<ContactPersonEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getContactPersonService();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, ContactPersonEntityComposite obj )
    {
        final String contactPersonId = obj.getIdentity();

        item.add( new SimpleLink( "firstName", obj.getFirstName() )
        {
            public void linkClicked()
            {
                //TODO bp. fixme
            }
        } );

        item.add( new SimpleLink( "lastName", obj.getLastName() )
        {
            public void linkClicked()
            {
                //TODO bp. fixme
            }
        } );

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        CheckBox loginEnabled = new CheckBox( "loginEnabled", new Model( obj.getLogin().isEnabled() ) );

        loginEnabled.setEnabled( false );
        item.add( loginEnabled );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                //TODO bp.
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Login Id", "Login Enabled", "Edit" );
    }
}