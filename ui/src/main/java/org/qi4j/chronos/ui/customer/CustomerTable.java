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
package org.qi4j.chronos.ui.customer;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionAdapter;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class CustomerTable extends ActionTable<CustomerEntityComposite>
{
    private SimpleDataProvider<CustomerEntityComposite> dataProvider;

    public CustomerTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction( new ActionAdapter( "Delete" )
        {
            public void performAction( AbstractSortableDataProvider dataProvider )
            {
                //TODO bp. fixme
            }
        } );
    }

    public AbstractSortableDataProvider<CustomerEntityComposite> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new SimpleDataProvider<CustomerEntityComposite>()
            {
                public EntityService<CustomerEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getCustomerService();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, CustomerEntityComposite obj )
    {
        item.add( new SimpleLink( "fullname", obj.getName() )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );

        item.add( new SimpleLink( "reference", obj.getReference() )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );

        //TODO bp. fix address
        item.add( new Label( "address", "Test Address " ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Full Name", "Reference", "Address", "" );
    }
}
