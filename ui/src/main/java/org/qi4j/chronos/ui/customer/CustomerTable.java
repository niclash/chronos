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
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public abstract class CustomerTable extends ActionTable<CustomerEntityComposite, String>
{
    private CustomerDataProvider provider;

    public CustomerTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<CustomerEntityComposite>( "Delete" )
        {
            public void performAction( List<CustomerEntityComposite> customers )
            {
                getCustomerService().delete( customers );

                info( "Selected customer(s) are deleted." );
            }
        } );
    }

    public AbstractSortableDataProvider<CustomerEntityComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new CustomerDataProvider()
            {
                public AccountEntityComposite getAccount()
                {
                    return CustomerTable.this.getAccount();
                }
            };
        }

        return provider;
    }

    public abstract AccountEntityComposite getAccount();

    public void populateItems( Item item, CustomerEntityComposite obj )
    {
        final String customerId = obj.getIdentity();

        item.add( createDetailLink( "name", obj.getName(), customerId ) );
        item.add( createDetailLink( "formalReference", obj.getReference(), customerId ) );
        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                CustomerEditPage editPage = new CustomerEditPage( this.getPage() )
                {
                    public CustomerEntityComposite getCustomer()
                    {
                        return getCustomerService().get( customerId );
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String displayValue, final String customerId )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                CustomerDetailPage detailPage = new CustomerDetailPage( this.getPage() )
                {
                    public CustomerEntityComposite getCustomer()
                    {
                        return getCustomerService().get( customerId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    private CustomerService getCustomerService()
    {
        return ChronosWebApp.getServices().getCustomerService();
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "" );
    }
}
